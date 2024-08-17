package com.acutecoder.irchat.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import java.io.InputStream

actual fun logInternal(tag: String, message: String) {
    System.err.println("$tag: $message")
}

actual fun InputStream.loadAsImageBitmap(): ImageBitmap {
    return loadImageBitmap(this)
}