package com.acutecoder.irchat.domain.model

import com.acutecoder.irchat.presentation.randomUUID

data class IRModel(
    val modelName: String,
    val modelInfo: String,
    val iconText: String,
    val moreInfo: String,
    val id: String = randomUUID(),
)
