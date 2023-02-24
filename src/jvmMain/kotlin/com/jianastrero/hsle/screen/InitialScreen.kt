package com.jianastrero.hsle.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.theme.Yellow
import kotlinx.coroutines.delay

@Composable
@Preview
fun InitialScreen(
    modifier: Modifier = Modifier,
    animDuration: Long = 500
) {
    var counter by remember { mutableStateOf(0) }
    val alpha by animateFloatAsState(
        if (counter % 2 == 0) 1f else 0f,
        animationSpec = tween(durationMillis = animDuration.toInt())
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(color = Yellow)
        Text(
            text = "LOADING SAVED GAMES",
            textAlign = TextAlign.Center,
            color = Yellow,
            modifier = Modifier.padding(12.dp)
                .alpha(alpha)
        )
    }

    LaunchedEffect(counter) {
        delay(animDuration)
        counter++
    }
}