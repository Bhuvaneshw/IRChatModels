package com.acutecoder.irchat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.acutecoder.irchat.presentation.launchIO
import java.io.File
import java.io.FileInputStream
import javax.swing.JFileChooser
import javax.swing.JFrame

@Composable
actual fun ImagePicker(onPickImage: (ImageFile) -> Unit, onCancel: () -> Unit) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val fileChooser = JFileChooser()

        fileChooser.fileFilter = javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "png", "gif", "bmp"
        )

        val result = fileChooser.showOpenDialog(JFrame("Image Picker"))

        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile: File = fileChooser.selectedFile
            scope.launchIO {
                val inputStream = { FileInputStream(selectedFile) }
                onPickImage(ImageFile(selectedFile.name, inputStream))
            }
        } else {
            onCancel()
        }

    }
}

