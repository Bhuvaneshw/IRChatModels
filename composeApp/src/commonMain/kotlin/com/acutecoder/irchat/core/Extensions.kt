package com.acutecoder.irchat.core

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


@OptIn(ExperimentalContracts::class)
inline fun <T : InputStream?, R> T.use(block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    var exception: Throwable? = null
    try {
        return block(this)
    } catch (e: Throwable) {
        exception = e
        throw e
    } finally {
        when {
            this == null -> {}
            exception == null -> close()
            else ->
                try {
                    close()
                } catch (closeException: Throwable) {
                    // cause.addSuppressed(closeException) // ignored here
                }
        }
    }
}

fun InputStream.readAllBytes(): ByteArray {
    val buffer = ByteArrayOutputStream()
    val data = ByteArray(1024)
    var bytesRead: Int

    while (true) {
        bytesRead = this.read(data)
        if (bytesRead == -1) {
            break
        }
        buffer.write(data, 0, bytesRead)
    }

    return buffer.toByteArray()
}