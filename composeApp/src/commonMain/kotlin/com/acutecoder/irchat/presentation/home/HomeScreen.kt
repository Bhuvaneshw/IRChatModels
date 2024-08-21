package com.acutecoder.irchat.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.acutecoder.irchat.domain.model.ApiEndPoint
import com.acutecoder.irchat.domain.model.IRModel
import com.acutecoder.irchat.presentation.chat.ChatScreen
import com.acutecoder.irchat.presentation.components.ErrorBox
import com.acutecoder.irchat.presentation.components.IpInputBox
import com.acutecoder.irchat.presentation.components.LoadingBox
import com.acutecoder.irchat.presentation.components.ModelBox
import com.acutecoder.irchat.presentation.components.StatusText
import com.acutecoder.irchat.presentation.components.Toolbar
import com.acutecoder.irchat.presentation.components.ToolbarIcon
import com.acutecoder.irchat.presentation.isIpAddress
import com.acutecoder.irchat.presentation.theme.Dimen
import com.acutecoder.irchat.presentation.theme.ThemeColors
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_change_ip
import irchatmodels.composeapp.generated.resources.ic_refresh
import org.jetbrains.compose.resources.painterResource

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        var showChangeIpDialog by remember { mutableStateOf(false) }
        val viewModel = rememberScreenModel { HomeViewModel() }
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            if (state.ipAddress != null && !state.isConnected) {
                viewModel.connect()
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(
                title = "IR Chat",
                rightContent = {
                    ToolbarIcon(
                        icon = Res.drawable.ic_change_ip,
                        onClick = { showChangeIpDialog = true }
                    )
                    ToolbarIcon(
                        icon = Res.drawable.ic_refresh,
                        onClick = { viewModel.connect() }
                    )
                }
            )

            StatusText(
                status = if (state.isConnected) "Connected to ${state.ipAddress}${if (state.port.isNotBlank()) ":${state.port}" else ""}" else "Connecting...",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.End)
            )

            when (val loadingStatus = state.loadingStatus) {
                is LoadingStatus.Loaded -> ModelGrid(
                    irModels = loadingStatus.irModels,
                    endPoint = viewModel.endPoint,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                is LoadingStatus.Error -> ErrorBox(
                    error = loadingStatus.error,
                    modifier = Modifier.fillMaxSize(),
                    centerAlign = true,
                    onRetry = { viewModel.connect() }
                )

                LoadingStatus.Loading -> LoadingBox(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        if (showChangeIpDialog || state.ipAddress == null) {
            ChangeIpDialog(
                ipAddress = state.ipAddress ?: "",
                port = state.port,
                dismiss = { showChangeIpDialog = false },
                onChangeIp = { ip, port -> viewModel.updateIp(ip, port) }
            )
        }
    }
}

@Composable
private fun ModelGrid(
    irModels: List<IRModel>,
    endPoint: ApiEndPoint,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = Dimen.HomeGridCellMinSize),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.widthIn(Dimen.HomeGridMinWidth, Dimen.HomeGridMaxWidth),
    ) {
        items(irModels, key = { it.id }) { model ->
            ModelBox(
                title = model.modelName,
                subTitle = model.modelInfo,
                iconBg = ThemeColors.primary,
                iconText = model.iconText,
                moreInfo = model.moreInfo,
                onExactModelClick = {
                    navigator.push(
                        ChatScreen(
                            endPoint = endPoint,
                            modelName = model.modelName,
                            modelType = "exact"
                        )
                    )
                },
                onApproxModelClick = {
                    navigator.push(
                        ChatScreen(
                            endPoint = endPoint,
                            modelName = model.modelName,
                            modelType = "approx"
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun ChangeIpDialog(
    ipAddress: String,
    port: String,
    dismiss: () -> Unit,
    onChangeIp: (String, String) -> Unit
) {
    var ipText by remember { mutableStateOf(ipAddress) }
    var portText by remember { mutableStateOf(port) }
    var isEditingPlainAddress by remember { mutableStateOf(!isIpAddress(ipAddress)) }

    Dialog(onDismissRequest = dismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = ThemeColors.background)
                .padding(start = 8.dp)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enter Ip Address",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_change_ip),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable { isEditingPlainAddress = !isEditingPlainAddress }
                        .padding(8.dp)
                )
            }

            if (isEditingPlainAddress)
                TextField(
                    value = ipText + if (portText.isNotBlank()) ":$portText" else "",
                    onValueChange = { newText ->
                        val parts = newText.split(":")
                        ipText = parts[0]
                        portText = if (parts.size > 1) parts[1] else ""
                    },
                    singleLine = true,
                    label = { Text("Enter the IP") },
                    modifier = Modifier.fillMaxWidth(),
                )
            else IpInputBox(
                ipAddress = { ipText },
                port = { portText },
                onValueChange = { newAddress, newPort ->
                    ipText = newAddress
                    portText = newPort
                }
            )

            Text(
                text = "Ok",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        dismiss()
                        onChangeIp(ipText, portText)
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = ThemeColors.primary
            )

        }
    }
}
