package com.acutecoder.irchat.data.repository

import com.acutecoder.irchat.domain.model.IRModel
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import com.acutecoder.irchat.presentation.withIO
import kotlinx.coroutines.delay

class DummyIRModelsRepositoryImpl : IRModelsRepository {

    override suspend fun connect() {
        withIO {
            delay(1000)
        }
    }

    override suspend fun loadModels(): List<IRModel> {
        delay(1000)

        return buildList {
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
        }
    }

    override suspend fun predict(): String {
        delay(1000)

        return (Math.random() * 100).toInt().toString()
    }

}
