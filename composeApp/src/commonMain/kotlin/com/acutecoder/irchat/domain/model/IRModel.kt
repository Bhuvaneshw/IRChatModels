package com.acutecoder.irchat.domain.model

import com.acutecoder.irchat.core.UUID

data class IRModel(
    val modelName: String,
    val modelInfo: String,
    val iconText: String,
    val moreInfo: String,
    val id: String = UUID.randomUUID(),
)
