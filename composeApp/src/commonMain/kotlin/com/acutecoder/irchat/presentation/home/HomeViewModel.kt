package com.acutecoder.irchat.presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import com.acutecoder.irchat.presentation.injectInstance
import com.acutecoder.irchat.presentation.launchIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ScreenModel {

    private val repository: IRModelsRepository = injectInstance()
    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun updateIp(ipAddress: String) {
        _state.update {
            it.copy(ipAddress = ipAddress)
        }

        if (!state.value.isConnected)
            connect()
    }

    fun connect() {
        updateState { copy(isConnected = false, loadingStatus = LoadingStatus.Loading) }

        screenModelScope.launchIO {
            try {
                repository.connect()
                updateState { copy(isConnected = true) }
                loadModels()
            } catch (e: Exception) {
                updateState { copy(isConnected = false) }
            }
        }
    }

    private fun loadModels() {
        screenModelScope.launchIO {
            try {
                val models = repository.loadModels()
                updateState { copy(loadingStatus = LoadingStatus.Loaded(models)) }
            } catch (e: Exception) {
                updateState {
                    copy(
                        loadingStatus = LoadingStatus.Error(e.message ?: "Unknown Error")
                    )
                }
            }
        }
    }

    private fun updateState(block: HomeState.() -> HomeState) {
        _state.update(block)
    }

}
