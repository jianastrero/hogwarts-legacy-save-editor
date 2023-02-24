package com.jianastrero.hsle

import androidx.compose.ui.unit.dp
import java.io.File

object Constants {
    val WINDOW_WIDTH = 1600.dp
    val WINDOW_HEIGHT = 900.dp

    const val LATEST_RELEASE_URL = "https://raw.githubusercontent.com/" +
            "jianastrero/hogwarts-legacy-save-editor/" +
            "main/src/jvmMain/resources/version.txt"

    val HL_SAVE_GAMES_DIR: String?
        get() {
            val appDataDirFile = File(System.getenv("APPDATA")).parentFile
            val saveDirectoryFile = File(appDataDirFile, "\\Local\\Hogwarts Legacy\\Saved\\SaveGames")
            return saveDirectoryFile.listFiles()?.firstOrNull { it.isDirectory }?.absolutePath
        }
}