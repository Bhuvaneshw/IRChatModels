package com.acutecoder.irchat.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Any?.log(message: Any?) {
    val stackTrace = Thread.currentThread().stackTrace[2]
    logInternal(
        tag = if (this is String) this else "$this",
        "$message (${stackTrace.fileName}:${stackTrace.lineNumber})"
    )
}

expect fun logInternal(tag: String, message: String)

fun String.titleCase(): String {
    return split(" ", "_").joinToString(separator = " ") { it[0].uppercase() + it.substring(1) }
}

suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T) {
    withContext(Dispatchers.IO, block)
}

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO, block = block)
}

inline fun <reified T> injectInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}
