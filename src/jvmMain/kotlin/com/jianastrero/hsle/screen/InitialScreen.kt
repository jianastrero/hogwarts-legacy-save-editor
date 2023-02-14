package com.jianastrero.hsle.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.component.HLSEButton

@Composable
@Preview
fun InitialScreen(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        HLSEButton(
            "Select Save Game File",
            onClick = {},
            modifier = Modifier.align(Alignment.Center)
        )
    }
}