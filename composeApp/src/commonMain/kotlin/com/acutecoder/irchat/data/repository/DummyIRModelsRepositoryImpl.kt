package com.acutecoder.irchat.data.repository

import com.acutecoder.irchat.core.Math
import com.acutecoder.irchat.domain.model.ApiEndPoint
import com.acutecoder.irchat.domain.model.IRModel
import com.acutecoder.irchat.domain.model.ResultBody
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import com.acutecoder.irchat.presentation.components.ImageFile
import com.acutecoder.irchat.presentation.withIO
import kotlinx.coroutines.delay

class DummyIRModelsRepositoryImpl : IRModelsRepository {

    override suspend fun connect(endPoint: ApiEndPoint): ResultBody {
        withIO {
            delay(1000)
        }

        return ResultBody.Success(true)
    }

    override suspend fun loadModels(endPoint: ApiEndPoint): ResultBody {
        delay(1000)

        return ResultBody.Success(buildList {
            repeat(11) {
                add(
                    IRModel(
                        modelName = "Digit Recognition $it",
                        modelInfo = "Simple digit recognition model",
                        iconText = "DR",
                        moreInfo = "Nothing to say"
                    )
                )
            }
        })
    }

    override suspend fun predict(
        endPoint: ApiEndPoint,
        modelName: String,
        modelType: String,
        imageFile: ImageFile,
    ): ResultBody {
        delay(1000)

        return ResultBody.Success((Math.random() * 100).toInt().toString())
    }

}
