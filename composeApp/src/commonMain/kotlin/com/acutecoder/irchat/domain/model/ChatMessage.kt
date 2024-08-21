package com.acutecoder.irchat.domain.model

sealed interface ChatMessage {

    val id: String

    data class UserMessage(
        private val imageBytes: suspend () -> ByteArray?,
        override val id: String,
    ) : ChatMessage {

        private var byteArray: ByteArray? = null

        suspend fun loadBytes(): ByteArray? {
            if (byteArray == null)
                byteArray = imageBytes()
            return byteArray
        }

    }

    data class ModelMessage(
        val result: String,
        override val id: String,
    ) : ChatMessage

}