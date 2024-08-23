package com.acutecoder.irchat.data.repository

import com.acutecoder.irchat.data.model.ConnectResultBody
import com.acutecoder.irchat.data.model.ListModelsResultBody
import com.acutecoder.irchat.data.model.PredictErrorResultBody
import com.acutecoder.irchat.data.model.PredictResultBody
import com.acutecoder.irchat.domain.model.ApiEndPoint
import com.acutecoder.irchat.domain.model.IRModel
import com.acutecoder.irchat.domain.model.ResultBody
import com.acutecoder.irchat.domain.model.client
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import com.acutecoder.irchat.presentation.components.ImageFile
import com.acutecoder.irchat.presentation.log
import com.acutecoder.irchat.presentation.withIO
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

class IRModelsRepositoryImpl : IRModelsRepository {

    override suspend fun connect(endPoint: ApiEndPoint): ResultBody = withIO {
        try {
            val response = endPoint.client.get(endPoint.routeConnect())
            val body = Json.decodeFromString<ConnectResultBody>(
                response.bodyAsText().apply { "Response".log(this) })
            ResultBody.Success(body.result == "Connected")
        } catch (e: Exception) {
            ResultBody.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun loadModels(endPoint: ApiEndPoint): ResultBody {
        try {
            val response = endPoint.client.post(endPoint.routeListModels())
            val body = Json.decodeFromString<ListModelsResultBody>(
                response.bodyAsText().apply { "Response".log(this) })

            return ResultBody.Success(body.result.map {
                IRModel(
                    modelName = it.modelName.trim(),
                    modelInfo = it.modelInfo.trim(),
                    iconText = it.modelName.split(" ", "_")
                        .joinFirst2Chars()
                        .uppercase(),
                    moreInfo = it.moreInfo.trim()
                )
            })
        } catch (e: Exception) {
            return ResultBody.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun predict(
        endPoint: ApiEndPoint,
        modelName: String,
        modelType: String,
        imageFile: ImageFile,
    ): ResultBody {
        try {
            val imageBytes = imageFile.bytes()

            val response = endPoint.client.submitFormWithBinaryData(
                url = endPoint.routePredict(),
                formData = formData {
                    append("modelName", modelName)
                    append("modelType", modelType)
                    append("image", imageBytes, Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"${imageFile.name}\"")
                    })
                })

            val bodyAsText = response.bodyAsText()

            try {
                val body = Json.decodeFromString<PredictResultBody>(bodyAsText)
                return ResultBody.Success(body.result)
            } catch (e: Exception) {
                try {
                    val body = Json.decodeFromString<PredictErrorResultBody>(bodyAsText)
                    return ResultBody.Error(body.error)
                } catch (e: Exception) {
                    return ResultBody.Error(e.message ?: "Unknown Error")
                }
            }
        } catch (e: Exception) {
            return ResultBody.Error(e.message ?: "Unknown Error")
        }

    }

}

private fun List<String>.joinFirst2Chars(): String {
    return filter { it.isNotBlank() }.map { it[0] }.let {
        when (it.size) {
            0 -> ""
            1 -> it[0].toString()
            else -> "${it[0]}${it[1]}"
        }
    }
}
