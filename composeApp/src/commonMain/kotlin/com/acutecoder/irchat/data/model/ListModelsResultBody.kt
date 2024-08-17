package com.acutecoder.irchat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ListModelsResultBody(
    val result: List<ModelInfo>
)
