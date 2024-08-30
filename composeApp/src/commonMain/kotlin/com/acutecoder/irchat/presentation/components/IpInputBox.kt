package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acutecoder.irchat.presentation.ifThen
import com.acutecoder.irchat.presentation.isDigitsOnly
import com.acutecoder.irchat.presentation.theme.ThemeColors
import com.acutecoder.irchat.presentation.tryRun

@Composable
fun IpInputBox(
    ipAddress: () -> String,
    port: () -> String,
    onValueChange: (String, String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var ipPart1 by rememberTextFieldValue(ipAddress().ipSplit(0))
    var ipPart2 by rememberTextFieldValue(ipAddress().ipSplit(1))
    var ipPart3 by rememberTextFieldValue(ipAddress().ipSplit(2))
    var ipPart4 by rememberTextFieldValue(ipAddress().ipSplit(3))
    var portPart by rememberTextFieldValue(port())

    var ipPart1Focus by remember { mutableStateOf(false) }
    var ipPart2Focus by remember { mutableStateOf(false) }
    var ipPart3Focus by remember { mutableStateOf(false) }
    var ipPart4Focus by remember { mutableStateOf(false) }
    var portPartFocus by remember { mutableStateOf(false) }

    val update = remember {
        {
            onValueChange(
                "${ipPart1.text}.${ipPart2.text}.${ipPart3.text}.${ipPart4.text}",
                portPart.text
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier = remember { Modifier.widthIn(60.dp, 70.dp) }

        IpPartInputField(
            modifier = modifier.ifThen(ipPart1Focus) {
                it.focusRequester(focusRequester)
            },
            value = ipPart1,
            onValueChange = validate {
                ipPart1 = it
                if (it.text.length == 3) {
                    ipPart1Focus = false
                    ipPart2Focus = true
                } else ipPart1Focus = true
                update()
            })
        Dot()

        IpPartInputField(
            modifier = modifier.ifThen(ipPart2Focus) {
                it.focusRequester(focusRequester)
            },
            value = ipPart2,
            onValueChange = validate {
                ipPart2 = it
                if (it.text.length == 3) {
                    ipPart2Focus = false
                    ipPart3Focus = true
                } else ipPart2Focus = true
                update()
            },
            onFocusPrevious = {
                ipPart1Focus = true
                ipPart2Focus = false
            })
        Dot()

        IpPartInputField(
            modifier = modifier.ifThen(ipPart3Focus) {
                it.focusRequester(focusRequester)
            },
            value = ipPart3,
            onValueChange = validate {
                ipPart3 = it
                if (it.text.length == 3) {
                    ipPart3Focus = false
                    ipPart4Focus = true
                } else ipPart3Focus = true
                update()
            },
            onFocusPrevious = {
                ipPart2Focus = true
                ipPart3Focus = false
            })
        Dot()

        IpPartInputField(
            modifier = modifier.ifThen(ipPart4Focus) {
                it.focusRequester(focusRequester)
            },
            value = ipPart4,
            onValueChange = validate {
                ipPart4 = it
                if (it.text.length == 3) {
                    ipPart4Focus = false
                    portPartFocus = true
                } else ipPart4Focus = true
                update()
            },
            onFocusPrevious = {
                ipPart3Focus = true
                ipPart4Focus = false
            })
        Dot(text = ":")

        IpPartInputField(
            modifier = Modifier.widthIn(120.dp, 200.dp).ifThen(portPartFocus) {
                it.focusRequester(focusRequester)
            },
            value = portPart,
            onValueChange = validate(65535, 5) {
                portPart = it
                update()
            },
            onFocusPrevious = {
                ipPart4Focus = true
                portPartFocus = false
            })
    }

    LaunchedEffect(ipPart1Focus, ipPart2Focus, ipPart3Focus, ipPart4Focus, portPartFocus) {
        focusRequester.tryRun { requestFocus() }
    }
}

@Suppress("NOTHING_TO_INLINE")
@Composable
private inline fun rememberTextFieldValue(text: String) =
    remember { mutableStateOf(TextFieldValue(text = text, selection = TextRange(text.length))) }

@Composable
private fun Dot(text: String = ".") {
    Text(text = text, modifier = Modifier.padding(2.dp), color = ThemeColors.dark)
}

private fun String.ipSplit(partIndex: Int): String {
    return split(".").let {
        if (partIndex < it.size && it[partIndex].isDigitsOnly())
            it[partIndex]
        else ""
    }
}

@Composable
private fun IpPartInputField(
    modifier: Modifier,
    value: TextFieldValue,
    onFocusPrevious: (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .ifThen(onFocusPrevious != null && value.text.isEmpty()) {
                it.onKeyEvent { event ->
                    if (event.key == Key.Backspace && value.text.isEmpty()) {
                        onFocusPrevious?.invoke()
                        true
                    } else false
                }
            },
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = ThemeColors.dark,
        ),
        singleLine = true,
        decorationBox = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, color = ThemeColors.dark, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
                content = { it() }
            )
        }
    )
}

private fun validate(
    maxValue: Int = 255,
    maxLength: Int = 3,
    onValueAccepted: (TextFieldValue) -> Unit
): (TextFieldValue) -> Unit = { value ->
    if (value.text.length <= maxLength && value.text.isDigitsOnly() &&
        (value.text.toIntOrNull() ?: 0) <= maxValue
    ) onValueAccepted(value)
}
