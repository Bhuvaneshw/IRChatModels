package com.acutecoder.irchat.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.acutecoder.irchat.core.InputStream
import com.acutecoder.irchat.core.readAllBytes
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image

actual fun logInternal(tag: String, message: String) {
    console.log("$tag: $message")
}

@Suppress("CLASSNAME")
external object console : JsAny {
    fun log(message: String)
    fun error(message: String)
}

actual fun InputStream.loadAsImageBitmap(): ImageBitmap {
    return Image.makeFromEncoded(readAllBytes()).toComposeImageBitmap()
}

actual suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Default, block)
}

actual fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.Default, block = block)
}

@OptIn(DelicateCoroutinesApi::class)
actual fun copyToClipboard(text: String) {
    GlobalScope.promise {
        try {
            window.navigator.clipboard.writeText(text).await<JsAny>()
            console.log("Text copied to clipboard")
        } catch (e: Throwable) {
            console.error("Failed to copy text to clipboard, $e")
        }
    }
}
