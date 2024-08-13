package com.acutecoder.irchat.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.acutecoder.irchat.home.presentation.components.ModelBox
import com.acutecoder.irchat.ui.theme.ThemeColors

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ModelBox(
                title = "Digit Recognition",
                subTitle = "Recognizes single digit",
                iconBg = ThemeColors.primary,
                iconText = "DR",
                moreInfo = "Exact model is 98% accurate\nApprox model is 96% accurate",
                onExactModelClick = {},
                onApproxModelClick = {}
            )
        }
    }

}
