package com.acutecoder.irchat.core

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ByteArrayOutputStream actual constructor() {
    private var buffer: ByteArray = ByteArray(32)
    private var count: Int = 0

    actual fun write(b: Int) {
        ensureCapacity(count + 1)
        buffer[count] = b.toByte()
        count++
    }

    actual fun write(b: ByteArray, off: Int, len: Int) {
        ensureCapacity(count + len)
        b.copyInto(buffer, count, off, off + len)
        count += len
    }

    actual fun toByteArray(): ByteArray {
        return buffer.copyOf(count)
    }

    actual fun reset() {
        count = 0
    }

    actual fun size(): Int {
        return count
    }

    actual fun close() {
        // No-op for ByteArrayOutputStream
    }

    private fun ensureCapacity(minCapacity: Int) {
        if (minCapacity - buffer.size > 0) {
            grow(minCapacity)
        }
    }

    private fun grow(minCapacity: Int) {
        val oldCapacity = buffer.size
        var newCapacity = oldCapacity * 2
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity
        }
        if (newCapacity < 0) {
            if (minCapacity < 0) throw OutOfMemoryError()
            newCapacity = Int.MAX_VALUE
        }
        buffer = buffer.copyOf(newCapacity)
    }
}
