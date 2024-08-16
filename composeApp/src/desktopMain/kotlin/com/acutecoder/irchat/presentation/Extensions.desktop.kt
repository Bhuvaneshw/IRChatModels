package com.acutecoder.irchat.presentation

actual fun logInternal(tag: String, message: String) {
    System.err.println("$tag: $message")
}