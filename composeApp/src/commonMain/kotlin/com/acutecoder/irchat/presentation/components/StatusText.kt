package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acutecoder.irchat.presentation.theme.ThemeColors

@Composable
fun StatusText(status: String, modifier: Modifier = Modifier) {
    Text(
        text = status,
        modifier = modifier.padding(8.dp),
        style = MaterialTheme.typography.labelSmall,
        color = ThemeColors.dark.copy(alpha = 0.7f),
    )
}
