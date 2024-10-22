package com.acutecoder.irchat.presentation.home

import com.acutecoder.irchat.domain.model.IRModel

data class HomeState(
    var ipAddress: String? = "10.70.249.59",
    var port: String = "5000",
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
