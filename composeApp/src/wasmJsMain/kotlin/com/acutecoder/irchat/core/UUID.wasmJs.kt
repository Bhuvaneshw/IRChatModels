package com.acutecoder.irchat.core

import kotlin.random.Random

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object UUID {
    actual fun randomUUID(): String {
        return buildString {
            repeat(8) { append(randomHex()) }
            append('-')
            repeat(4) { append(randomHex()) }
            append('-')
            append('4') // UUID version 4
            repeat(3) { append(randomHex()) }
            append('-')
            append(randomHex(8, 11)) // UUID variant (8, 9, A, or B)
            repeat(3) { append(randomHex()) }
            append('-')
            repeat(12) { append(randomHex()) }
        }
    }

    private fun randomHex(min: Int = 0, max: Int = 15): Char {
        val value = Random.nextInt(min, max + 1)
        return value.toString(16)[0]
    }
}