package com.acutecoder.irchat.presentation.components

import android.webkit.URLUtil
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.acutecoder.irchat.presentation.launchIO

@Composable
actual fun ImagePicker(onPickImage: (ImageFile) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            scope.launchIO {
                it?.let { uri ->
                    val inputStream =
                        context.contentResolver.openInputStream(uri) ?: return@launchIO
                    val fileName = URLUtil.guessFileName(uri.path, null, "image/*")

                    onPickImage(ImageFile(fileName ?: "Unknown", inputStream))
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
