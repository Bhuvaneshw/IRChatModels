package com.acutecoder.irchat.presentation

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.InputStream

actual fun logInternal(tag: String, message: String) {
    Log.e(tag, message)
}

actual fun InputStream.loadAsImageBitmap(): ImageBitmap {
    return BitmapFactory.decodeStream(this).asImageBitmap()
}