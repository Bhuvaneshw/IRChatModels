package com.acutecoder.irchat.domain.model

import org.jetbrains.compose.resources.DrawableResource

sealed interface ChatMessage {

    data class UserMessage(
        val imagePath: DrawableResource,
    ) : ChatMessage

    data class ModelMessage(
        val result: String,
    ) : ChatMessage

}