package com.acutecoder.irchat.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import com.acutecoder.irchat.core.InputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

actual fun Any?.logInternal(message: Any?) {
    val stackTrace = Thread.currentThread().stackTrace[2]
    System.err.println(
        if (this is String) this else "$this" +
                ": $message (${stackTrace.fileName}:${stackTrace.lineNumber})"
    )
}

actual fun InputStream.loadAsImageBitmap(): ImageBitmap {
    return loadImageBitmap(this)
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
