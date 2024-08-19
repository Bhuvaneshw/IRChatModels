package com.acutecoder.irchat.presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.acutecoder.irchat.domain.model.ApiEndPoint
import com.acutecoder.irchat.domain.model.IRModel
import com.acutecoder.irchat.domain.model.ResultBody
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
    var endPoint = ApiEndPoint(state.value.ipAddress ?: "localhost", state.value.port)
        private set

    fun updateIp(ipAddress: String, port: String) {
        _state.update {
            it.copy(ipAddress = ipAddress, port = port)
        }

        connect()
    }

    fun connect() {
        updateState { copy(isConnected = false, loadingStatus = LoadingStatus.Loading) }

        screenModelScope.launchIO {
            try {
                updateEndPointIfNecessary()

                val body = repository.connect(endPoint)
                if (body is ResultBody.Error) {
                    updateState {
                        copy(
                            loadingStatus = LoadingStatus.Error(body.error),
                            isConnected = false
                        )
                    }
                    return@launchIO
                }

                val isConnected = if (body is ResultBody.Success<*>) {
                    if (body.result is Boolean) body.result else false
                } else false
                updateState { copy(isConnected = isConnected) }

                loadModels()
            } catch (e: Exception) {
                updateState { copy(isConnected = false) }
            }
        }
    }

    private fun loadModels() {
        screenModelScope.launchIO {
            try {
                val body = repository.loadModels(endPoint)
                if (body is ResultBody.Error) {
                    updateState {
                        copy(
                            loadingStatus = LoadingStatus.Error(body.error),
                            isConnected = false
                        )
                    }
                    return@launchIO
                }

                val models: List<IRModel> = if (body is ResultBody.Success<*>) {
                    if (body.result is List<*> && body.result.isNotEmpty() && body.result[0] is IRModel)
                        @Suppress("UNCHECKED_CAST")
                        body.result as List<IRModel>
                    else emptyList()
                } else emptyList()

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

    private fun updateEndPointIfNecessary() {
        if (!endPoint.equals(state.value.ipAddress ?: "localhost", state.value.port))
            endPoint = ApiEndPoint(state.value.ipAddress ?: "localhost", state.value.port)
    }

}
