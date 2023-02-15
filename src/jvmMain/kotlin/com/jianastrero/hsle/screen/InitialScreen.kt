package com.jianastrero.hsle.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.component.HLSEButton
import com.jianastrero.hsle.model.HLSaveFileData
import com.jianastrero.hsle.notification.Notifications
import com.jianastrero.hsle.save_file.HLSaveFile
import java.io.File

@Composable
@Preview
fun InitialScreen(
    onSelectLoadFile: (directory: String) -> String?,
    onValidSaveFileSelected: (originalFilePath: String, hlSaveFileData: HLSaveFileData) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        HLSEButton(
            text = "Select Save Game File",
            onClick = OnClick@{
                val appDataDirFile = File(System.getenv("APPDATA")).parentFile
                val saveDirectoryFile = File(appDataDirFile, "\\Local\\Hogwarts Legacy\\Saved\\SaveGames")
                val saveDirectoryMainFile = saveDirectoryFile.listFiles()?.filter { it.isDirectory }?.firstOrNull()
                val selectedFilePath = onSelectLoadFile(
                    saveDirectoryMainFile?.absolutePath ?: saveDirectoryFile.absolutePath
                )
                    ?: return@OnClick
                try {
                    val hlSaveFileData = HLSaveFile.read(selectedFilePath)
                    onValidSaveFileSelected(selectedFilePath, hlSaveFileData)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Notifications.error("You selected an invalid save file")
                }
            },
            modifier = Modifier.align(Alignment.Center)
        )
    }
}