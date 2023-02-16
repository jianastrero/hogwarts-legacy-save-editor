package com.jianastrero.hsle.enumerations

import androidx.compose.ui.graphics.Color
import com.jianastrero.hsle.theme.Blue
import com.jianastrero.hsle.theme.Green
import com.jianastrero.hsle.theme.Red
import com.jianastrero.hsle.theme.Yellow

sealed class HouseCrest(
    val value: String,
    val color: Color,
    val image: String
) {
    object Gryffindor : HouseCrest(
        value = "Gryffindor",
        image = "house_crest/gryffindor.png",
        color = Red
    )
    object Hufflepuff : HouseCrest(
        value = "Hufflepuff",
        image = "house_crest/hufflepuff.png",
        color = Yellow
    )
    object Ravenclaw : HouseCrest(
        value = "Ravenclaw",
        image = "house_crest/ravenclaw.png",
        color = Blue
    )
    object Slytherin : HouseCrest(
        value = "Slytherin",
        image = "house_crest/slytherin.png",
        color = Green
    )

    companion object {
        fun values() = arrayOf(Gryffindor, Hufflepuff, Ravenclaw, Slytherin)
    }
}
