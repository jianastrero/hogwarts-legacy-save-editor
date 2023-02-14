package com.jianastrero.hsle

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.component.NavHost
import com.jianastrero.hsle.model.HLSaveFileData
import com.jianastrero.hsle.model.rememberNavController
import com.jianastrero.hsle.nav.HLSENav
import com.jianastrero.hsle.screen.InitialScreen
import com.jianastrero.hsle.screen.MainScreen
import com.jianastrero.hsle.sqlite.HLSESQLite
import com.jianastrero.hsle.theme.NotoSerifTypography

@Composable
@Preview
fun App(
    onSelectLoadFile: (directory: String) -> String?,
    onClose: () -> Unit
) {
    val backgroundPainter = painterResource("background.png")
    val titlePainter = painterResource("title.png")
    val closePainter = painterResource("close_button.png")
    val navController = rememberNavController()
    var hlSaveFileData: HLSaveFileData? by remember { mutableStateOf(null) }

    MaterialTheme(typography = NotoSerifTypography) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = backgroundPainter,
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .height(64.dp)
                        .padding(12.dp)
                ) {
                    Image(
                        painter = titlePainter,
                        contentDescription = "Hogwarts Legacy Save Editor",
                        modifier = Modifier.padding(bottom = 12.dp, start = 12.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f).height(1.dp))
                    Image(
                        painter = closePainter,
                        contentDescription = "Close",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = onClose)
                    )
                }
                NavHost(
                    startDestination = HLSENav.InitialScreen,
                    navController = navController,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    composable(route = HLSENav.InitialScreen) {
                        InitialScreen(
                            onSelectLoadFile = onSelectLoadFile,
                            onValidSaveFileSelected = {
                                HLSESQLite.initiate(it.tempSqliteFilePath)
                                hlSaveFileData = it
                                navController.navigate(HLSENav.MainScreen)
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    composable(route = HLSENav.MainScreen) {
                        hlSaveFileData?.let {
                            MainScreen(modifier = Modifier.fillMaxSize())
                        } ?: kotlin.run {
                            navController.navigate(HLSENav.InitialScreen)
                            // TODO: Show error, file is not readable
                        }
                    }
                }
            }
        }
    }
}