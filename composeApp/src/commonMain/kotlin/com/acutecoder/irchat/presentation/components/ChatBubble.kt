package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import com.acutecoder.irchat.domain.model.ChatMessage
import com.acutecoder.irchat.presentation.launchIO
import com.acutecoder.irchat.presentation.theme.ThemeColors
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_copy
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatBubble(message: ChatMessage) {
    when (message) {
        is ChatMessage.ModelMessage -> ModelChatBubble(message)
        is ChatMessage.UserMessage -> UserChatBubble(message)
    }
}

@Composable
fun PlainChatBubble(isAlignEnd: Boolean = false, block: @Composable ColumnScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isAlignEnd) Arrangement.End else Arrangement.Start,
    ) {
        Column(
            modifier = Modifier
                .width(180.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(ThemeColors.secondaryContainer)
        ) {
            block()
        }
    }
}

@Composable
private fun UserChatBubble(message: ChatMessage.UserMessage) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        launchIO {
            imageBitmap = loadImageBitmap(message.getInputStream())
        }
    }

    PlainChatBubble(isAlignEnd = true) {
        Text(
            text = "You",
            modifier = Modifier.padding(8.dp)
        )

        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        } ?: LoadingBox(modifier = Modifier.padding(8.dp), size = 36.dp)
    }
}

@Composable
private fun ModelChatBubble(message: ChatMessage.ModelMessage) {
    PlainChatBubble {
        Text(
            text = "Model",
            modifier = Modifier.padding(8.dp)
        )

        SelectionContainer {
            Text(
                text = message.result,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            )
        }

        Icon(
            painter = painterResource(Res.drawable.ic_copy),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .padding(3.dp)
                .size(30.dp)
                .clip(CircleShape)
                .clickable {
                }
                .padding(8.dp)
        )
    }
}
