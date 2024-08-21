package com.acutecoder.irchat.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.acutecoder.irchat.core.InputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual fun Any?.logInternal(message: Any?) {
    val stackTrace = Thread.currentThread().stackTrace[2]
    Log.e(
        if (this is String) this else "$this",
        "$message (${stackTrace.fileName}:${stackTrace.lineNumber})"
    )
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

actual fun copyToClipboard(text: String) {
    val context: Context = injectInstance()
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(
        ClipData("Copied text", arrayOf("text/plain"), ClipData.Item(text))
    )
}

actual suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

actual fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO, block = block)
}
