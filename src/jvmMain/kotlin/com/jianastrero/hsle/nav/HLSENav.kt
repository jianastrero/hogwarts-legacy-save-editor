package com.jianastrero.hsle.nav

sealed interface HLSENav {
    object InitialScreen : HLSENav
    object MainScreen : HLSENav

    sealed class Main(val title: String) : HLSENav {
        object PlayerData : Main("Player Data")
        object Resources : Main("Resources")

        companion object {
            fun values() = arrayOf(PlayerData, Resources)
        }
    }
}