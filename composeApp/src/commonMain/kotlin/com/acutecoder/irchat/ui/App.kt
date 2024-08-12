package com.acutecoder.irchat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.acutecoder.irchat.home.presentation.HomeScreen
import com.acutecoder.irchat.ui.theme.IRChatAppTheme
import com.acutecoder.irchat.ui.theme.ThemeColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    IRChatAppTheme {
        Column(
            modifier = Modifier
                .background(ThemeColors.background)
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            Navigator(HomeScreen())
        }
    }
}
