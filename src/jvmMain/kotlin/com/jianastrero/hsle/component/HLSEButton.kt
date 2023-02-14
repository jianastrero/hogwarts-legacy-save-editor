package com.jianastrero.hsle.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun HLSEButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    full: Boolean = true,
    tint: Color? = null
) {
    val buttonPainter = painterResource(if (full) "button.svg" else "button-half.svg")
    Box(
        modifier = Modifier
            .size(if (full) 360.dp else 168.dp, 50.dp)
            .then(modifier)
    ) {
        Image(
            painter = buttonPainter,
            contentDescription = null,
            colorFilter = tint?.let { ColorFilter.tint(it, BlendMode.Modulate) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .padding(bottom = 6.dp)
        )
        Box(
            Modifier.padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clickable(onClick = onClick, enabled = enabled)
        )
    }
}