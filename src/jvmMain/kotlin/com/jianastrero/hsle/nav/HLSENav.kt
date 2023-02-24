package com.jianastrero.hsle.nav

import com.jianastrero.hsle.sqlite.SQLite

sealed interface HLSENav {
    object LoadingScreen : HLSENav

    object MainScreen : HLSENav

    sealed class Main(val title: String, val sqlite: SQLite) : HLSENav {
        object Empty : Main("Empty", SQLite.None)
        class PlayerData(sqlite: SQLite) : Main(title, sqlite) {
            companion object {
                val title = "Player Data"
            }
        }
        class Resources(sqlite: SQLite) : Main(title, sqlite) {
            companion object {
                val title = "Resources"
            }
        }
    }
}