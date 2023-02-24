package com.jianastrero.hsle.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.enumerations.LoadablePageState
import com.jianastrero.hsle.theme.Yellow

@Composable
fun LoadablePage(
    state: LoadablePageState,
    loadingMessage: String = "Loading...",
    savingMessage: String = "Saving...",
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        state.`is`(
            targetState = LoadablePageState.Loaded,
            content = content,
            modifier = Modifier.fillMaxSize()
        )
        state.`is`(
            targetState = LoadablePageState.Loading,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                CircularProgressIndicator(color = Yellow)
                Text(
                    text = loadingMessage,
                    color = Yellow
                )
            }
        }
        state.`is`(
            targetState = LoadablePageState.Saving,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                CircularProgressIndicator(color = Yellow)
                Text(
                    text = savingMessage,
                    color = Yellow
                )
            }
        }
    }
}

@Composable
private fun LoadablePageState.`is`(
    targetState: LoadablePageState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    AnimatedVisibility(
        this == targetState,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    )
}