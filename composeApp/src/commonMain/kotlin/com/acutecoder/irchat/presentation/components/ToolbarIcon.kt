package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ToolbarIcon(icon: DrawableResource, onClick: () -> Unit) {
    Icon(
        painter = painterResource(icon),
        contentDescription = null,
        modifier = Modifier
            .padding(6.dp)
            .size(45.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(10.dp)
    )
}