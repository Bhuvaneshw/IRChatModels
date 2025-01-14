package com.acutecoder.irchat.presentation.components

import androidx.compose.runtime.Composable

@Composable
expect fun ImagePicker(onPickImage: (ImageFile) -> Unit, onCancel: () -> Unit)

class ImageFile(
    val name: String,
    val bytes: () -> ByteArray,
)
