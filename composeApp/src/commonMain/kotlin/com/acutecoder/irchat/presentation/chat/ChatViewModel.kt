package com.acutecoder.irchat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.acutecoder.irchat.domain.model.ChatMessage
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import com.acutecoder.irchat.presentation.components.ImageFile
import com.acutecoder.irchat.presentation.injectInstance
import com.acutecoder.irchat.presentation.launchIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class ChatViewModel : ScreenModel {

    private val repository: IRModelsRepository = injectInstance()
    val chatMessages = mutableStateListOf<ChatMessage>()
    private var _state = MutableStateFlow<ChatState>(ChatState.ReceivedReply)
    val state = _state.asStateFlow()
    var loadingId by mutableStateOf("")

    fun sendMessage(image: ImageFile): String {
        updateState { ChatState.WaitingForReply }

        screenModelScope.launchIO {
            chatMessages.add(ChatMessage.UserMessage(image.stream, UUID.randomUUID().toString()))
            loadingId = UUID.randomUUID().toString()

            try {
                val reply = repository.predict()
                chatMessages.add(ChatMessage.ModelMessage(result = reply, id = loadingId))

                updateState { ChatState.ReceivedReply }
            } catch (e: Exception) {
                updateState { ChatState.Error(e.message ?: "Unknown Error") }
            }
        }

        return loadingId
    }

    private fun updateState(block: ChatState.() -> ChatState) {
        _state.update(block)
    }

}

