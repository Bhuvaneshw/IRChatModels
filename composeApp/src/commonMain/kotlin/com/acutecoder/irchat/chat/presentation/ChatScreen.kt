package com.acutecoder.irchat.chat.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.acutecoder.irchat.chat.presentation.components.ChatBubble
import com.acutecoder.irchat.chat.presentation.components.ImageSelectionBox
import com.acutecoder.irchat.chat.presentation.model.ChatMessage
import com.acutecoder.irchat.ui.components.Toolbar
import com.acutecoder.irchat.ui.titleCase
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_back
import irchatmodels.composeapp.generated.resources.sampleimage
import kotlinx.coroutines.launch

class ChatScreen(
    private val modelName: String,
    private val modelType: String,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        val sampleData = remember { mutableStateListOf<ChatMessage>() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            Toolbar(
                title = modelType.titleCase() + " " + modelName.titleCase(),
                backIcon = Res.drawable.ic_back,
                onBackClick = { navigator.pop() }
            )

            Column(
                modifier = Modifier
                    .widthIn(min = 200.dp, max = 1020.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(20.dp),
                ) {
                    items(sampleData) { mesage ->
                        ChatBubble(mesage)
                    }
                }

                ImageSelectionBox(onSendImage = {
                    sampleData.add(ChatMessage.UserMessage(Res.drawable.sampleimage))
                    sampleData.add(ChatMessage.ModelMessage("011"))

                    scope.launch {
                        listState.animateScrollToItem(sampleData.lastIndex)
                    }
                })
            }
        }

    }

}
