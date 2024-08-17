package com.acutecoder.irchat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PredictErrorResultBody(
    val error: String
)
