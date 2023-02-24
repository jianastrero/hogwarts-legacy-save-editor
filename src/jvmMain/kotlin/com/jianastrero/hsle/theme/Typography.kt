package com.jianastrero.hsle.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

val InterFontFamily = FontFamily(
    Font("fonts/Inter-Regular.ttf"),
    Font("fonts/Inter-Bold.ttf", FontWeight.Bold),
    Font("fonts/Inter-SemiBold.ttf", FontWeight.SemiBold),
    Font("fonts/Inter-Medium.ttf", FontWeight.Medium),
    Font("fonts/Inter-Black.ttf", FontWeight.Black),
    Font("fonts/Inter-ExtraBold.ttf", FontWeight.ExtraBold),
    Font("fonts/Inter-ExtraLight.ttf", FontWeight.ExtraLight),
    Font("fonts/Inter-Light.ttf", FontWeight.Light),
    Font("fonts/Inter-Thin.ttf", FontWeight.Thin),
)

val InterTypography = Typography(defaultFontFamily = InterFontFamily)