package com.acutecoder.irchat.presentation.components

import androidx.compose.runtime.Composable
import com.acutecoder.irchat.core.InputStream

@Composable
expect fun ImagePicker(onPickImage: (ImageFile) -> Unit, onCancel: () -> Unit)

class ImageFile(
    val name: String,
    val newStream: () -> InputStream?,
)
