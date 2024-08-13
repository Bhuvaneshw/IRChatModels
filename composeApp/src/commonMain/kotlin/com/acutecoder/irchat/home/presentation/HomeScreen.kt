package com.acutecoder.irchat.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.acutecoder.irchat.chat.presentation.ChatScreen
import com.acutecoder.irchat.home.presentation.components.ModelBox
import com.acutecoder.irchat.home.presentation.components.StatusText
import com.acutecoder.irchat.ui.components.Toolbar
import com.acutecoder.irchat.ui.theme.ThemeColors
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_change_ip
import org.jetbrains.compose.resources.painterResource

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showChangeIpDialog by remember { mutableStateOf(false) }
        var ipAddress by remember { mutableStateOf<String?>("11.11.11.11") }
        val status by remember { mutableStateOf("Connected to $ipAddress:5000") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Toolbar(
                title = "IR Chat",
                rightContent = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_change_ip),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(45.dp)
                            .clip(CircleShape)
                            .clickable { showChangeIpDialog = true }
                            .padding(10.dp)
                    )
                }
            )

            StatusText(
                status = status,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.End)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 320.dp),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .widthIn(min = 200.dp, max = 1020.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                repeat(11) {
                    item {
                        ModelBox(
                            title = "Digit Recognition",
                            subTitle = "Recognizes single digit",
                            iconBg = ThemeColors.primary,
                            iconText = "DR",
                            moreInfo = "Exact model is 98% accurate\nApprox model is 96% accurate",
                            onExactModelClick = {
                                navigator.push(
                                    ChatScreen(modelName = "digit_recognition", modelType = "exact")
                                )
                            },
                            onApproxModelClick = {
                                navigator.push(
                                    ChatScreen(modelName = "digit_recognition", modelType = "approx")
                                )
                            }
                        )
                    }
                }
            }
        }

        if (showChangeIpDialog || ipAddress == null) {
            ChangeIpDialog(
                ipAddress = ipAddress ?: "",
                dismiss = { showChangeIpDialog = false },
                onChangeIp = { ipAddress = it }
            )
        }
    }
}

@Composable
private fun ChangeIpDialog(ipAddress: String, dismiss: () -> Unit, onChangeIp: (String) -> Unit) {
    var ipText by remember { mutableStateOf(ipAddress) }

    Dialog(onDismissRequest = dismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = ThemeColors.background)
                .padding(start = 8.dp, top = 8.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "Enter Ip Address",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            TextField(
                value = ipText,
                onValueChange = { newText ->
                    ipText = newText
                },
                singleLine = true,
                label = { Text("Enter the IP") },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "Ok",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        dismiss()
                        onChangeIp(ipText)
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = ThemeColors.primary
            )

        }
    }
}
