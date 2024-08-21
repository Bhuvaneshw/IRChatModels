package com.acutecoder.irchat.core

import kotlin.random.Random

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Math {
    actual fun random(): Double = Random.nextDouble()
}
