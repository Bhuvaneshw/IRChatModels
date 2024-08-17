package com.acutecoder.irchat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PredictResultBody(
    val result: String
)
