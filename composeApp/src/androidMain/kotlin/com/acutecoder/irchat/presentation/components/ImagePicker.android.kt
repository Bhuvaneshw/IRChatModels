package com.acutecoder.irchat.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.acutecoder.irchat.presentation.getFileName
import com.acutecoder.irchat.presentation.launchIO

@Composable
actual fun ImagePicker(onPickImage: (ImageFile) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            scope.launchIO {
                it?.let { uri ->
                    val newStream = { context.contentResolver.openInputStream(uri) }
                    val fileName = context.getFileName(uri) ?: "Unknown.jpg"

                    onPickImage(ImageFile(fileName, newStream))
                } ?: onCancel()
            }
        }

    LaunchedEffect(Unit) {
        launcher.launch(
            PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }
}
