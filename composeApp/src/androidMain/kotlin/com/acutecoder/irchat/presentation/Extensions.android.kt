package com.acutecoder.irchat.presentation

import android.util.Log

actual fun logInternal(tag: String, message: String) {
    Log.e(tag, message)
}