package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorBox(
    error: String,
    modifier: Modifier = Modifier,
    centerAlign: Boolean = false,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = error,
            color = Color.Red,
            textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start,
        )

        onRetry?.let {
            Button(onClick = it, modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Retry")
            }
        }
    }
}
