package com.acutecoder.irchat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.acutecoder.irchat.domain.model.ApiEndPoint
import com.acutecoder.irchat.domain.model.ChatMessage
import com.acutecoder.irchat.domain.model.ResultBody
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import com.acutecoder.irchat.presentation.components.ImageFile
import com.acutecoder.irchat.presentation.injectInstance
import com.acutecoder.irchat.presentation.launchIO
import com.acutecoder.irchat.presentation.randomUUID
import com.acutecoder.irchat.presentation.withIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel(
    private val repository: IRModelsRepository = injectInstance()
) : ScreenModel {

    val chatMessages = mutableStateListOf<ChatMessage>()
    private var _state = MutableStateFlow<ChatState>(ChatState.ReceivedReply)
    val state = _state.asStateFlow()
    var loadingId by mutableStateOf("")

    fun sendMessage(
        endPoint: ApiEndPoint,
        modelName: String,
        modelType: String,
        imageFile: ImageFile
    ): String {
        updateState { ChatState.WaitingForReply }

        screenModelScope.launchIO {
            val imageStream = suspend { withIO { imageFile.toByteArray() } }

            chatMessages.add(
                ChatMessage.UserMessage(
                    imageStream,
                    randomUUID()
                )
            )
            loadingId = randomUUID()

            try {
                val body = repository.predict(
                    endPoint = endPoint,
                    modelName = modelName,
                    modelType = modelType,
                    imageFile = imageFile
                )

                val reply = if (body is ResultBody.Success<*>) {
                    if (body.result is String) body.result
                    else "Error"
                } else if (body is ResultBody.Error) {
                    body.error
                } else "Error"

                updateState { ChatState.ReceivedReply }

                chatMessages.add(ChatMessage.ModelMessage(result = reply, id = loadingId))
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

