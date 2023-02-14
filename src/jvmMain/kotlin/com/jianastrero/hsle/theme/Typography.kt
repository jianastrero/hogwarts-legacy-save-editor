package com.jianastrero.hsle.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

val NotoSerifFontFamily = FontFamily(
    Font("fonts/NotoSerifKhojki-Regular.ttf"),
    Font("fonts/NotoSerifKhojki-Bold.ttf", FontWeight.Bold),
    Font("fonts/NotoSerifKhojki-SemiBold.ttf", FontWeight.SemiBold),
    Font("fonts/NotoSerifKhojki-Medium.ttf", FontWeight.Medium),
)

val NotoSerifTypography = Typography(defaultFontFamily = NotoSerifFontFamily)