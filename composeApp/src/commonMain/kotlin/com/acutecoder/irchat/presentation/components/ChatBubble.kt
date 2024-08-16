package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.acutecoder.irchat.domain.model.ChatMessage
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
private fun UserChatBubble(message: ChatMessage.UserMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Column(
            modifier = Modifier
                .width(180.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(ThemeColors.secondaryContainer)
        ) {
            Text(
                text = "You",
                modifier = Modifier.padding(8.dp)
            )

            Image(
                painter = painterResource(message.imagePath),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
private fun ModelChatBubble(message: ChatMessage.ModelMessage) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(ThemeColors.secondaryContainer)
    ) {
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
