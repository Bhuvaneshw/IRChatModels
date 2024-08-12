package com.acutecoder.irchat

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.acutecoder.irchat.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "IR Chat Models",
    ) {
        App()
    }
}
