package com.acutecoder.irchat.domain.model

import java.util.UUID

data class IRModel(
    val modelName: String,
    val modelInfo: String,
    val iconText: String,
    val moreInfo: String,
    val id: String = UUID.randomUUID().toString(),
)
