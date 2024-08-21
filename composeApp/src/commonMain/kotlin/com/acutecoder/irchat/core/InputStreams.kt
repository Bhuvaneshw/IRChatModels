package com.acutecoder.irchat.core

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect abstract class InputStream() {
    abstract fun read(): Int
    open fun read(b: ByteArray): Int
    open fun read(b: ByteArray, off: Int, len: Int): Int
    open fun close()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ByteArrayInputStream(buf: ByteArray) : InputStream
