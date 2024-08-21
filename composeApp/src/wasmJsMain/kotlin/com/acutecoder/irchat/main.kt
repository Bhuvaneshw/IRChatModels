package com.acutecoder.irchat

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.acutecoder.irchat.di.initKoin
import com.acutecoder.irchat.presentation.App
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()

    ComposeViewport(document.body!!) {
        App()
    }
}