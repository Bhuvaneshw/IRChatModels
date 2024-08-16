package com.acutecoder.irchat.domain.model

import com.acutecoder.irchat.presentation.readAsByteArray
import java.io.ByteArrayInputStream
import java.io.InputStream

sealed interface ChatMessage {

    val id: String

    data class UserMessage(
        private val imageStream: InputStream,
        override val id: String,
    ) : ChatMessage {

        private var byteArray: ByteArray? = null

        suspend fun getInputStream(): ByteArrayInputStream {
            if (byteArray == null)
                byteArray = imageStream.readAsByteArray()
            return ByteArrayInputStream(byteArray!!)
        }

    }

    data class ModelMessage(
        val result: String,
        override val id: String,
    ) : ChatMessage

}