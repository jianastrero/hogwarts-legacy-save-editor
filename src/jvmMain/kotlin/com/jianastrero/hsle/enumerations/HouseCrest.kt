package com.jianastrero.hsle.enumerations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jianastrero.hsle.icon.HouseCrestIcon
import com.jianastrero.hsle.icon.vector.Gryffindor
import com.jianastrero.hsle.icon.vector.Hufflepuff
import com.jianastrero.hsle.icon.vector.Ravenclaw
import com.jianastrero.hsle.icon.vector.Slytherin
import com.jianastrero.hsle.theme.Blue
import com.jianastrero.hsle.theme.BlueLight
import com.jianastrero.hsle.theme.Green
import com.jianastrero.hsle.theme.GreenLight
import com.jianastrero.hsle.theme.Red
import com.jianastrero.hsle.theme.RedLight
import com.jianastrero.hsle.theme.Yellow
import com.jianastrero.hsle.theme.YellowLight

sealed class HouseCrest(
    val value: String,
    val color: Color,
    val colorLight: Color,
    val imageVector: ImageVector
) {
    object Gryffindor : HouseCrest(
        value = "Gryffindor",
        imageVector = HouseCrestIcon.Gryffindor,
        color = Red,
        colorLight = RedLight
    )
    object Hufflepuff : HouseCrest(
        value = "Hufflepuff",
        imageVector = HouseCrestIcon.Hufflepuff,
        color = Yellow,
        colorLight = YellowLight
    )
    object Ravenclaw : HouseCrest(
        value = "Ravenclaw",
        imageVector = HouseCrestIcon.Ravenclaw,
        color = Blue,
        colorLight = BlueLight
    )
    object Slytherin : HouseCrest(
        value = "Slytherin",
        imageVector = HouseCrestIcon.Slytherin,
        color = Green,
        colorLight = GreenLight
    )

    companion object {
        fun values() = arrayOf(Gryffindor, Hufflepuff, Ravenclaw, Slytherin)
    }
}
