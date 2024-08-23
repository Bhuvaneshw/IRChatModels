package com.acutecoder.irchat.domain.model

sealed interface ChatMessage {

    val id: String

    data class UserMessage(
        val imageBytes: () -> ByteArray,
        override val id: String,
    ) : ChatMessage

    data class ModelMessage(
        val result: String,
        override val id: String,
    ) : ChatMessage

}