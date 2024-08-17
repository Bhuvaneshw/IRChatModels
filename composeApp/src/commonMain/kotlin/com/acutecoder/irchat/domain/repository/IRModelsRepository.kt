package com.acutecoder.irchat.domain.repository

import com.acutecoder.irchat.domain.model.ApiEndPoint
import com.acutecoder.irchat.domain.model.ResultBody
import com.acutecoder.irchat.presentation.components.ImageFile

interface IRModelsRepository {

    suspend fun connect(endPoint: ApiEndPoint): ResultBody

    suspend fun loadModels(endPoint: ApiEndPoint): ResultBody

    suspend fun predict(
        endPoint: ApiEndPoint,
        modelName: String,
        modelType: String,
        imageFile: ImageFile,
    ): ResultBody

}
