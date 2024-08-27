package com.acutecoder.irchat

import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.acutecoder.irchat.di.initKoin
import com.acutecoder.irchat.presentation.App

fun main() {
    initKoin()

    application {
        LocalApplication = this

        Window(
            onCloseRequest = ::exitApplication,
            title = "IR Chat Models",
        ) {
            App()
        }
    }
}

var LocalApplication: ApplicationScope? = null
    private set
