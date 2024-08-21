package com.acutecoder.irchat.core

import java.io.OutputStream
import java.io.ByteArrayInputStream as JvmByteInputStream
import java.io.InputStream as JvmInputStream

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual abstract class InputStream actual constructor() : JvmInputStream() {
    actual abstract override fun read(): Int

    actual override fun read(b: ByteArray): Int {
        return super.read(b)
    }

    actual override fun read(b: ByteArray, off: Int, len: Int): Int {
        return super.read(b, off, len)
    }

    actual override fun close() {
        super.close()
    }
}

class InputStreamDelegate(private val stream: JvmInputStream) : InputStream() {
    override fun read(): Int {
        return stream.read()
    }

    override fun read(b: ByteArray): Int {
        return stream.read(b)
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        return stream.read(b, off, len)
    }

    override fun close() {
        stream.close()
    }

    override fun readAllBytes(): ByteArray {
        return stream.readAllBytes()
    }

    override fun readNBytes(len: Int): ByteArray {
        return stream.readNBytes(len)
    }

    override fun readNBytes(b: ByteArray?, off: Int, len: Int): Int {
        return stream.readNBytes(b, off, len)
    }

    override fun skip(n: Long): Long {
        return stream.skip(n)
    }

    override fun skipNBytes(n: Long) {
        stream.skipNBytes(n)
    }

    override fun available(): Int {
        return stream.available()
    }

    override fun mark(readlimit: Int) {
        stream.mark(readlimit)
    }

    override fun reset() {
        stream.reset()
    }

    override fun markSupported(): Boolean {
        return stream.markSupported()
    }

    override fun transferTo(out: OutputStream?): Long {
        return stream.transferTo(out)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ByteArrayInputStream actual constructor(buf: ByteArray) : InputStream() {
    private val stream = JvmByteInputStream(buf)

    override fun read(): Int {
        return stream.read()
    }

    override fun read(b: ByteArray): Int {
        return stream.read(b)
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        return stream.read(b, off, len)
    }

    override fun close() {
        stream.close()
    }

    override fun readAllBytes(): ByteArray {
        return stream.readAllBytes()
    }

    override fun readNBytes(len: Int): ByteArray {
        return stream.readNBytes(len)
    }

    override fun readNBytes(b: ByteArray?, off: Int, len: Int): Int {
        return stream.readNBytes(b, off, len)
    }

    override fun skip(n: Long): Long {
        return stream.skip(n)
    }

    override fun skipNBytes(n: Long) {
        stream.skipNBytes(n)
    }

    override fun available(): Int {
        return stream.available()
    }

    override fun mark(readlimit: Int) {
        stream.mark(readlimit)
    }

    override fun reset() {
        stream.reset()
    }

    override fun markSupported(): Boolean {
        return stream.markSupported()
    }

    override fun transferTo(out: OutputStream?): Long {
        return stream.transferTo(out)
    }
}