@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.acutecoder.irchat.core

expect object UUID {
    fun randomUUID(): String
}
