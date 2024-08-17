package com.acutecoder.irchat.domain.model

sealed interface ResultBody {

    data class Success<T>(
        val result: T
    ) : ResultBody

    data class Error(
        val error: String
    ) : ResultBody

}
