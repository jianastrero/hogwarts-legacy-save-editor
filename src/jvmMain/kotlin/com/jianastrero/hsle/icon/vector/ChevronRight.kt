package com.jianastrero.hsle.icon.vector

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Rounded.ChevronRight: ImageVector
    get() {
        if (_chevronRight != null) {
            return _chevronRight!!
        }
        _chevronRight = Builder(name = "ChevronRightFill0Wght400Grad0Opsz48",
                defaultWidth = 48.0.dp, defaultHeight = 48.0.dp, viewportWidth = 960.0f,
                viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(375.0f, 720.0f)
                lineToRelative(-43.0f, -43.0f)
                lineToRelative(198.0f, -198.0f)
                lineToRelative(-198.0f, -198.0f)
                lineToRelative(43.0f, -43.0f)
                lineToRelative(241.0f, 241.0f)
                lineToRelative(-241.0f, 241.0f)
                close()
            }
        }
        .build()
        return _chevronRight!!
    }

private var _chevronRight: ImageVector? = null
