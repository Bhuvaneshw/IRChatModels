package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acutecoder.irchat.presentation.theme.ThemeColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    backIcon: DrawableResource? = null,
    onBackClick: (() -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ThemeColors.secondaryContainer)
            .padding(end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        backIcon?.let { icon ->
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable(enabled = onBackClick != null) {
                        onBackClick?.invoke()
                    }
                    .padding(10.dp),
                tint = ThemeColors.dark,
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(vertical = 14.dp, horizontal = if (backIcon != null) 7.dp else 14.dp)
                .weight(1f),
            color = ThemeColors.dark,
        )

        rightContent?.invoke()
    }

}