package com.acutecoder.irchat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.acutecoder.irchat.presentation.components.ChatBubble
import com.acutecoder.irchat.presentation.components.ErrorBox
import com.acutecoder.irchat.presentation.components.ImageSelectionBox
import com.acutecoder.irchat.presentation.components.LoadingBox
import com.acutecoder.irchat.presentation.components.PlainChatBubble
import com.acutecoder.irchat.presentation.components.Toolbar
import com.acutecoder.irchat.presentation.titleCase
import irchatmodels.composeapp.generated.resources.Res
import irchatmodels.composeapp.generated.resources.ic_back
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
        val viewModel = rememberScreenModel { ChatViewModel() }
        val chatMessages = viewModel.chatMessages
        val loadingState by viewModel.state.collectAsState()

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
                    items(chatMessages, key = { it.id }) { message ->
                        ChatBubble(message)
                    }

                    loadingState.let {
                        if (it is ChatState.Error)
                            item(key = { "Error" }) {
                                PlainChatBubble {
                                    Text(
                                        text = "Error",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    ErrorBox(error = it.error)
                                }
                            }
                        else if (it is ChatState.WaitingForReply)
                            item(key = { viewModel.loadingId }) {
                                PlainChatBubble {
                                    Text(
                                        text = "Model",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    LoadingBox(modifier = Modifier.padding(12.dp), size = 36.dp)
                                }
                            }
                    }
                }

                ImageSelectionBox(
                    enabled = loadingState is ChatState.ReceivedReply,
                    onSendImage = {
                        viewModel.sendMessage(it)
                        scope.launch {
                            if (chatMessages.size > 0)
                                listState.animateScrollToItem(chatMessages.lastIndex)
                        }
                    })
            }
        }
    }
}
