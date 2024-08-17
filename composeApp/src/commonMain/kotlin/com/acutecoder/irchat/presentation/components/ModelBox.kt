package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.acutecoder.irchat.presentation.theme.ThemeColors
import com.acutecoder.irchat.presentation.titleCase
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_info
import org.jetbrains.compose.resources.painterResource

@Composable
fun ModelBox(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    iconText: String,
    iconBg: Color,
    moreInfo: String,
    onExactModelClick: () -> Unit,
    onApproxModelClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = ThemeColors.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBox(iconText = iconText, iconBg = iconBg)
            ModelTitle(title = title, subTitle = subTitle)
            InfoBox(moreInfo = moreInfo)
        }

        ModelButtons(
            onExactModelClick = onExactModelClick,
            onApproxModelClick = onApproxModelClick
        )
    }

}


@Composable
private fun InfoBox(moreInfo: String) {
    var showDialog by remember { mutableStateOf(false) }

    Icon(
        painter = painterResource(Res.drawable.ic_info),
        contentDescription = "",
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .clickable { showDialog = true }
            .padding(8.dp),
        tint = ThemeColors.dark.copy(alpha = 0.6f)
    )

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = ThemeColors.background)
                    .padding(start = 8.dp, top = 8.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Model Info",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = moreInfo,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(horizontal = 8.dp)
                )

                Text(
                    text = "Close",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { showDialog = false }
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = ThemeColors.primary
                )
            }
        }
    }

}

@Composable
private fun RowScope.ModelTitle(title: String, subTitle: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .weight(1f)
    ) {
        Text(
            text = title.titleCase(),
            modifier = Modifier
                .padding(bottom = 2.dp),
            fontWeight = FontWeight.SemiBold,
        )

        Text(text = subTitle)
    }
}

@Composable
private fun ColumnScope.ModelButtons(
    onExactModelClick: () -> Unit,
    onApproxModelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
    ) {
        Text(
            text = "Exact Model",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = ThemeColors.primary.copy(alpha = 0.9f))
                .clickable(onClick = onExactModelClick)
                .padding(12.dp),
            textAlign = TextAlign.Center,
            color = ThemeColors.white,
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = "Approx Model",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = ThemeColors.primary)
                .clickable(onClick = onApproxModelClick)
                .padding(12.dp),
            textAlign = TextAlign.Center,
            color = ThemeColors.white,
        )

    }
}

@Composable
private fun IconBox(iconText: String, iconBg: Color) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(iconBg),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = iconText,
            color = ThemeColors.white,
        )
    }
}
