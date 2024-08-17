package com.acutecoder.irchat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ModelInfo(
    val modelName: String,
    val modelInfo: String,
    val moreInfo: String,
)
