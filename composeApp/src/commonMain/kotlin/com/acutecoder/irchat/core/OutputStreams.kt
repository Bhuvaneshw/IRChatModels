package com.acutecoder.irchat.core

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ByteArrayOutputStream() {
    fun write(b: Int)
    fun write(b: ByteArray, off: Int, len: Int)
    fun toByteArray(): ByteArray
    fun reset()
    fun size(): Int
    fun close()
}
