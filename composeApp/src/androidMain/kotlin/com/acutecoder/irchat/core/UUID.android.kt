@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.acutecoder.irchat.core

import java.util.UUID as JvmUUID

actual object UUID {
    actual fun randomUUID(): String = JvmUUID.randomUUID().toString()
}
