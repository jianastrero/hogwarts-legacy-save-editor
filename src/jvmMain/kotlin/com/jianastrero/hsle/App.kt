package com.jianastrero.hsle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.jianastrero.hsle.component.NotificationItem
import com.jianastrero.hsle.model.HLSaveFileData
import com.jianastrero.hsle.model.rememberNavController
import com.jianastrero.hsle.nav.HLSENav
import com.jianastrero.hsle.notification.Notifications
import com.jianastrero.hsle.save_file.HLSaveFile
import com.jianastrero.hsle.screen.InitialScreen
import com.jianastrero.hsle.screen.MainScreen
import com.jianastrero.hsle.sqlite.HLSESQLite
import com.jianastrero.hsle.theme.NotoSerifTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App(
    onSelectLoadFilePath: (directory: String) -> String?,
    onSelectSaveFilePath: () -> String?,
    onClose: () -> Unit
) {
    val backgroundPainter = painterResource("background.png")
    val titlePainter = painterResource("title.png")
    val closePainter = painterResource("close_button.png")
    val navController = rememberNavController()
    var originalFilePath: String? by remember { mutableStateOf(null) }
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
                            onSelectLoadFile = onSelectLoadFilePath,
                            onValidSaveFileSelected = { _originalFilePath, _hlSaveFileData ->
                                HLSESQLite.initiate(_hlSaveFileData.tempSqliteFilePath)
                                originalFilePath = _originalFilePath
                                hlSaveFileData = _hlSaveFileData
                                navController.navigate(HLSENav.MainScreen)
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    composable(route = HLSENav.MainScreen) {
                        hlSaveFileData?.let {
                            MainScreen(
                                onBack = {
                                    originalFilePath = null
                                    hlSaveFileData = null
                                    HLSESQLite.close()
                                    navController.navigate(HLSENav.InitialScreen)
                                },
                                onBackup = {
                                    originalFilePath?.let(HLSaveFile::backup)
                                    Notifications.success("Successfully backup. You could find your backups on \"backups\" folder.")
                                },
                                onSave = {
                                    val saveFilePath = onSelectSaveFilePath().let {
                                        it?.let {
                                            if (it.endsWith(".sav")) {
                                                it
                                            } else {
                                                "$it.sav"
                                            }
                                        }
                                    }
                                    val _hlSaveFileData = hlSaveFileData
                                    if (saveFilePath != null && _hlSaveFileData != null) {
                                        HLSaveFile.write(saveFilePath, _hlSaveFileData)
                                        Notifications.success("Successfully saved \"save game\" file")
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        } ?: kotlin.run {
                            navController.navigate(HLSENav.InitialScreen)
                            Notifications.error("You selected an invalid save file")
                        }
                    }
                }
            }
            AnimatedVisibility(
                Notifications.notifications.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.wrapContentHeight()
                        .width(320.dp)
                        .padding(12.dp)
                ) {
                    items(Notifications.notifications) {
                        NotificationItem(
                            notification = it,
                            modifier = Modifier.animateItemPlacement(tween())
                        )
                    }
                }
            }
        }
    }
}