package com.jianastrero.hsle

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App(
    onClose: () -> Unit
) {
    val backgroundPainter = painterResource("background.png")
    val titlePainter = painterResource("title.png")
    val closePainter = painterResource("close_button.png")

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = backgroundPainter,
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .align(Alignment.TopCenter)
                    .padding(12.dp)
            ) {
                Image(
                    painter = titlePainter,
                    contentDescription = "Hogwarts Legacy Save Editor",
                    modifier = Modifier.padding(bottom = 12.dp, start = 12.dp)
                )
                Spacer(modifier = Modifier.weight(1f).height(1.dp))
                Image(
                    painter = closePainter,
                    contentDescription = "Close",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onClose)
                )
            }
        }
    }
}