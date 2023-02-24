package com.jianastrero.hsle.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jianastrero.hsle.extensions.dashedBorder
import com.jianastrero.hsle.icon.HouseCrestIcon
import com.jianastrero.hsle.icon.vector.Ravenclaw
import com.jianastrero.hsle.theme.HLSETheme
import com.jianastrero.hsle.theme.Yellow

@Composable
fun HoverButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    leadingIcon: ImageVector? = null,
    leadingIconTint: Color? = null,
    trailingIcon: ImageVector? = null,
    trailingIconTint: Color? = null,
    selected: Boolean = false,
    defaultColor: Color = Color.White,
    selectedColor: Color = Yellow,
    hoveredColor: Color = selectedColor.copy(alpha = 0.7f),
    hoveredLetterSpacing: Float = 0.5f
) {
    val density = LocalDensity.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    var size by remember { mutableStateOf(DpSize(200.dp, 200.dp)) }
    val letterSpacing by animateFloatAsState(
        targetValue = if (isHovered || selected) hoveredLetterSpacing else 0f,
        animationSpec = tween(durationMillis = 600)
    )
    val color by animateColorAsState(
        targetValue = if (selected) selectedColor else if (isHovered) hoveredColor else defaultColor,
        animationSpec = tween(durationMillis = 600)
    )
    val borderCuts by animateFloatAsState(
        targetValue = if (isHovered || selected) 3f else 1f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .hoverable(interactionSource)
            .clickable(onClick = onClick)
            .background(Color.Black.copy(alpha = 0.4f))
            .dashedBorder(
                width = 2.dp,
                color = color,
                on = size.width / borderCuts - 8.dp / 2,
                off = 8.dp
            )
            .onSizeChanged {
                size = with(density) {
                    DpSize(it.width.toDp(), it.height.toDp())
                }
            }
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    horizontal = 48.dp,
                    vertical = 12.dp
                )
        ) {
            HoverButtonContent(
                text = text,
                color = color,
                letterSpacing = letterSpacing,
                textAlign = textAlign,
                hoveredLetterSpacing = hoveredLetterSpacing,
                alpha = 0f
            )
            HoverButtonContent(
                text = text,
                color = color,
                letterSpacing = letterSpacing,
                textAlign = textAlign,
                hoveredLetterSpacing = hoveredLetterSpacing,
                alpha = 1f
            )
        }
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "$text leading icon",
                tint = leadingIconTint ?: Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(horizontal = 12.dp)
                    .size(24.dp)
            )
        }
        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = "$text trailing icon",
                tint = trailingIconTint ?: Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
                    .padding(horizontal = 12.dp)
                    .size(24.dp)
            )
        }
    }
}

@Composable
private fun BoxScope.HoverButtonContent(
    text: String,
    color: Color,
    letterSpacing: Float,
    textAlign: TextAlign,
    hoveredLetterSpacing: Float,
    alpha: Float
) {
    Text(
        text = text.uppercase(),
        color = color,
        letterSpacing = if (alpha == 0f) hoveredLetterSpacing.sp else letterSpacing.sp,
        textAlign = textAlign,
        modifier = Modifier.align(Alignment.Center)
            .alpha(alpha)
    )
}

@Preview
@Composable
private fun HoverButtonPreview() {
    val backgroundPainter = painterResource("background.jpg")

    HLSETheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.fillMaxSize()) {
                HoverButton(
                    text = "Hello World",
                    onClick = {},
                    modifier = Modifier
                )
                HoverButton(
                    text = "Hello World",
                    onClick = {},
                    leadingIcon = HouseCrestIcon.Ravenclaw,
                    modifier = Modifier
                )
            }
        }
    }
}