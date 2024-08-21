package com.acutecoder.irchat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.acutecoder.irchat.core.ByteArrayInputStream
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader
import org.w3c.files.get

@Composable
actual fun ImagePicker(onPickImage: (ImageFile) -> Unit, onCancel: () -> Unit) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val input = document.createElement("input") as HTMLInputElement
        input.type = "file"
        input.accept = "image/*" // Accept image files only

        // Trigger a click to open the file picker
        input.onchange = {
            val file = input.files?.get(0)
            if (file != null) {
                val reader = FileReader()
                reader.onload = {
                    val arrayBuffer = Uint8Array(reader.result as ArrayBuffer)
                    val byteArray = ByteArray(arrayBuffer.byteLength) { arrayBuffer[it] }
                    scope.launch {
                        onPickImage(
                            ImageFile(
                                file.name
                            ) { ByteArrayInputStream(byteArray) }
                        )
                    }
                }
                reader.readAsArrayBuffer(file)
            } else {
                onCancel()
            }
        }

        // Add the input to the DOM, trigger the file picker, and remove it
        document.body?.appendChild(input)
        input.click()
        document.body?.removeChild(input)
    }
}
