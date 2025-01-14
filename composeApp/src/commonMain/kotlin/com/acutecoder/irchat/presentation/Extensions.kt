package com.acutecoder.irchat.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun log(message: String, tag: String = "") = tag.log(message)

fun Any.log(message: Any?) {
    logInternal(if (this is String) this else "", "$message")
}

internal expect fun logInternal(tag: String, message: String)

fun String.titleCase(): String {
    return split(" ", "_").joinToString(separator = " ") { it[0].uppercase() + it.substring(1) }
}

expect suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T): T

expect fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit)

inline fun <reified T> injectInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}

expect fun ByteArray.toImageBitmap(): ImageBitmap

inline fun String.isDigitsOnly(predicate: (Char) -> Boolean = { it.isDigit() }) = all(predicate)

inline fun Modifier.ifThen(condition: Boolean, other: (Modifier) -> Modifier) =
    if (condition)
        this then other(this)
    else this

inline fun <T> T.tryRun(block: T.() -> Unit) {
    try {
        block()
    } catch (_: Exception) {
    }
}

expect fun copyToClipboard(text: String)

fun isIpAddress(ipAddress: String): Boolean {
    return ipAddress.split(" ", ".", ":").all { it.isDigitsOnly() }
}

expect fun randomUUID(): String

expect fun finishApplication()
