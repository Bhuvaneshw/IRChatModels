package com.acutecoder.irchat.presentation.chat

sealed interface ChatState {

    data object WaitingForReply : ChatState
    data object ReceivedReply : ChatState
    data class Error(val error: String) : ChatState

}