package com.acutecoder.irchat

import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.acutecoder.irchat.di.initKoin
import com.acutecoder.irchat.presentation.App
import java.awt.Dimension

fun main() {
    initKoin()

    application {
        LocalApplication = this

        Window(
            onCloseRequest = ::exitApplication,
            title = "IR Chat Models",
            state = rememberWindowState()
        ) {
            window.minimumSize = Dimension(400, 550)
            App()
        }
    }
}

var LocalApplication: ApplicationScope? = null
    private set
