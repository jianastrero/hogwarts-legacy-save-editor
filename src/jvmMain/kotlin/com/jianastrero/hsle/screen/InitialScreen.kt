package com.jianastrero.hsle.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.component.HLSEButton
import com.jianastrero.hsle.model.HLSaveFileData
import com.jianastrero.hsle.save_file.HLSaveFile

@Composable
@Preview
fun InitialScreen(
    onSelectLoadFile: (directory: String) -> String?,
    onValidSaveFileSelected: (hlSaveFileData: HLSaveFileData) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        HLSEButton(
            text = "Select Save Game File",
            onClick = OnClick@{
                val selectedFile = onSelectLoadFile("%APPDATA%\\..\\Local\\Hogwarts Legacy\\Saved\\SaveGames")
                    ?: return@OnClick
                try {
                    val hlSaveFileData = HLSaveFile.read(selectedFile)
                    onValidSaveFileSelected(hlSaveFileData)
                } catch (e: Exception) {
                    e.printStackTrace()
                    // TODO: Show file invalid error message
                }
            },
            modifier = Modifier.align(Alignment.Center)
        )
    }
}