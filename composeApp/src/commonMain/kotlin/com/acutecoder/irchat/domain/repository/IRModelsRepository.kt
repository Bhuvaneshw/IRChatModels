package com.acutecoder.irchat.domain.repository

import com.acutecoder.irchat.domain.model.IRModel

interface IRModelsRepository {

    suspend fun connect()

    suspend fun loadModels(): List<IRModel>

    suspend fun predict(): String

}
