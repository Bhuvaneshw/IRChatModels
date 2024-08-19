package com.acutecoder.irchat.presentation

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
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

fun Context.getFileName(uri: Uri): String? {
    var fileName: String? = null

    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                fileName = it.getString(nameIndex)
            }
        }
    }

    return fileName
}
