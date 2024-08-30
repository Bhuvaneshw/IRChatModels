package com.acutecoder.irchat.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acutecoder.irchat.presentation.theme.ThemeColors

@Composable
fun SideBar(modifier: Modifier = Modifier) {
    ModalDrawerSheet(drawerContainerColor = ThemeColors.secondaryContainer, modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight().padding(16.dp)
        ) {
            textItem("About the project")
            textItem(
                " Team Members & Roles:\n" + "\n" +
                        "Person-1: Frontend Developer (Android Kotlin Multiplatform)\n" +
                        "Person-2: Backend Developer\n" +
                        "Person-3: Data Preprocessing Engineer (Python)\n" +
                        "Person-4: QA and Release Engineer"
            )
            textItem(
                "Project Overview: ImageRecognizer is a multiplatform application designed to recognize images using a Convolutional Neural Network (CNN). The project is developed in a collaborative environment, with each team member responsible for specific tasks.\n" +
                        "\n"
            )
            textItem(
                "Frontend: As the frontend developer, your responsibility is to design and implement the user interface in Android Kotlin Multiplatform. The focus is on creating an intuitive and responsive design that integrates seamlessly with the backend and the data processing pipeline.\n" +
                        "\n" +
                        "Backend: Person-2 develops the backend services, handling data storage, and managing requests between the frontend and the image recognition model.\n" +
                        "\n" +
                        "Data Preprocessing: Person-3 implements the data preprocessing algorithms in Python, preparing images for analysis by the CNN model.\n" +
                        "\n" +
                        "Testing and Release: Person-4 tests the application, ensuring it functions as intended across all platforms, and is responsible for releasing the final version of the project.\n" +
                        "\n"
            )

        }

    }

}

private fun LazyListScope.textItem(text: String) {
    item {
        Text(text = text, modifier = Modifier.padding(5.dp), color = ThemeColors.dark)
    }
}
