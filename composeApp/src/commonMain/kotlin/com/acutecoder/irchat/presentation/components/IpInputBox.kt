package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun IpDialog(onDismiss: () -> Unit) {
    var ipPart1 by remember { mutableStateOf("") }
    var ipPart2 by remember { mutableStateOf("") }
    var ipPart3 by remember { mutableStateOf("") }
    var ipPart4 by remember { mutableStateOf("") }
    var ipPart5 by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextBox(value = ipPart1, onValueChange = {
            if (it.isDigitsOnly() && (it.toIntOrNull() ?: 0) <= maxOf(255)) {
                ipPart1 = it
            }
        })
        Text(text = ".", modifier = Modifier.padding(2.dp))
        TextBox(value = ipPart2, onValueChange = {
            if (it.isDigitsOnly() && (it.toIntOrNull() ?: 0) <= maxOf(255)) {
                ipPart2 = it
            }
        })
        Text(text = ".", modifier = Modifier.padding(2.dp))
        TextBox(value = ipPart3, onValueChange = {
            if (it.isDigitsOnly() && (it.toIntOrNull() ?: 0) <= maxOf(255)) {
                ipPart3 = it
            }
        })
        Text(text = ".", modifier = Modifier.padding(2.dp))
        TextBox(value = ipPart4, onValueChange = {
            if (it.isDigitsOnly() && (it.toIntOrNull() ?: 0) <= maxOf(255)) {
                ipPart4 = it
            }
        })
        Text(text = ":", modifier = Modifier.padding(2.dp))
        TextBox(value = ipPart5, onValueChange = {
            if (it.isDigitsOnly() && (it.toIntOrNull() ?: 0) <= maxOf(255)) {
                ipPart5 = it
            }
        })
    }
}

@Composable
fun TextBox(value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            lineHeightStyle = LineHeightStyle(
                LineHeightStyle.Alignment.Bottom,
                LineHeightStyle.Trim.None
            )
        ),
    )
}
