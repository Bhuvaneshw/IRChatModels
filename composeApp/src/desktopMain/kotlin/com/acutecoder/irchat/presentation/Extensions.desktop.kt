package com.acutecoder.irchat.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.UUID

internal actual fun logInternal(tag: String, message: String) {
    val stackTrace = Thread.currentThread().stackTrace[2]
    System.err.println("$tag: $message (${stackTrace.fileName}:${stackTrace.lineNumber})")
}

actual fun ByteArray.loadAsImageBitmap(): ImageBitmap {
    return Image.makeFromEncoded(this).toComposeImageBitmap()
}

actual fun copyToClipboard(text: String) {
    val stringSelection = StringSelection(text)
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(stringSelection, null)
}

actual suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

actual fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO, block = block)
}

actual fun randomUUID(): String = UUID.randomUUID().toString()
