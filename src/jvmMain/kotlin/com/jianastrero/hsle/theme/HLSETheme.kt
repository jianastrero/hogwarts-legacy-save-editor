package com.jianastrero.hsle.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HLSETheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = InterTypography,
        content = content
    )
}