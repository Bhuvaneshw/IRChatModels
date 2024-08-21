package com.acutecoder.irchat.core

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual abstract class InputStream {
    protected var pos: Int = 0
    protected var mark: Int = 0

    actual open fun read(b: ByteArray, off: Int, len: Int): Int {
        var i = 0
        while (i < len) {
            val c = read()
            if (c == -1) {
                return if (i == 0) -1 else i
            }
            b[off + i] = c.toByte()
            i++
        }
        return i
    }

    actual open fun close() {}
    actual abstract fun read(): Int

    actual open fun read(b: ByteArray): Int {
        return read(b, 0, b.size)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ByteArrayInputStream actual constructor(private val buf: ByteArray) : InputStream() {
    override fun read(): Int {
        return if (pos < buf.size) {
            buf[pos++].toInt() and 0xFF
        } else {
            -1
        }
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        if (pos >= buf.size) {
            return -1
        }
        val avail = buf.size - pos
        val toCopy = if (len > avail) avail else len
        buf.copyInto(b, off, pos, pos + toCopy)
        pos += toCopy
        return toCopy
    }

    override fun close() {
        // No-op for ByteArrayInputStream
    }
}
