package com.acutecoder.irchat.presentation.home

import com.acutecoder.irchat.domain.model.IRModel

data class HomeState(
    var ipAddress: String? = "11.11.11.11",
    var port: String = "1111",
    var isConnected: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.Loading,
)

sealed class LoadingStatus {
    data object Loading : LoadingStatus()
    data class Loaded(val irModels: List<IRModel>) : LoadingStatus()
    data class Error(val error: String) : LoadingStatus()

    override fun toString(): String {
        return when (this) {
            is Loaded -> ""
            is Error -> "Error: $error"
            Loading -> "Loading..."
        }
    }
}
