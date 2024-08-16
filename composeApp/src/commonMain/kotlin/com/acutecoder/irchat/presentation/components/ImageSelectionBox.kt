package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.acutecoder.irchat.presentation.theme.ThemeColors
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_add_image
import irchatmodels.composeapp.generated.resources.ic_send
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageSelectionBox(onSendImage: () -> Unit) {
    var path by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 20.dp)
            .clip(CircleShape)
            .background(color = ThemeColors.secondaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { path = "C:/TTF/Images/sample1.jpg" },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_add_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(10.dp)
            )

            Text(
                text = path?.let { it.substring(it.lastIndexOf("/") + 1) } ?: "Select Image",
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                color = ThemeColors.dark.copy(alpha = if (path == null) 0.6f else 1f)
            )
        }

        Icon(
            painter = painterResource(Res.drawable.ic_send),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .background(color = ThemeColors.primary)
                .aspectRatio(1.5f)
                .clickable { onSendImage() }
                .padding(10.dp),
            tint = ThemeColors.white,
        )
    }
}

