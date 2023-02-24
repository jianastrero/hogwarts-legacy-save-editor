package com.jianastrero.hsle.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.component.HoverButton
import com.jianastrero.hsle.component.NavHost
import com.jianastrero.hsle.component.rememberNavController
import com.jianastrero.hsle.enumerations.HouseCrest
import com.jianastrero.hsle.icon.vector.ChevronRight
import com.jianastrero.hsle.nav.HLSENav
import com.jianastrero.hsle.notification.Notifications
import com.jianastrero.hsle.theme.BlueLight
import com.jianastrero.hsle.theme.GreenLight
import com.jianastrero.hsle.theme.Yellow
import com.jianastrero.hsle.theme.YellowLight
import java.awt.Desktop
import java.net.URI

@Composable
fun MainScreen(
    saveGameFile: SaveGameFile,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val bmcPainter = painterResource("bmc.svg")
    var selectedCharacter: Character? by remember { mutableStateOf(null) }

    Row(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(0.3f)
                .padding(horizontal = 12.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
                    .fillMaxWidth()
            ) {
                items(saveGameFile.characterList) {character ->
                    val houseCrest = HouseCrest.values()
                        .first { it.value.lowercase() == character.house.lowercase() }
                    val sqlite = saveGameFile.saveFileList.firstOrNull { it.characterID == character.id }
                        ?.characterSaveFileData
                        ?.sqlite
                        ?: throw  RuntimeException("Don't delete the temp files nerd")
                    val isSelected = selectedCharacter == character

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        HoverButton(
                            text = character.toString(),
                            onClick = { selectedCharacter = character },
                            selected = isSelected,
                            leadingIcon = houseCrest.imageVector,
                            leadingIconTint = houseCrest.colorLight,
                            trailingIcon = Icons.Rounded.ArrowDropDown,
                            modifier = Modifier.fillMaxWidth()
                        )
                        AnimatedVisibility(
                            visible = isSelected,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                HoverButton(
                                    text = "Player Data",
                                    onClick = {
                                        navController.navigate(HLSENav.Main.PlayerData(sqlite))
                                    },
                                    selectedColor = GreenLight,
                                    trailingIcon = Icons.Rounded.ChevronRight,
                                    modifier = Modifier.fillMaxWidth(0.9f)
                                )
                                HoverButton(
                                    text = "Resources",
                                    onClick = {
                                        navController.navigate(HLSENav.Main.Resources(sqlite))
                                    },
                                    selectedColor = GreenLight,
                                    trailingIcon = Icons.Rounded.ChevronRight,
                                    modifier = Modifier.fillMaxWidth(0.9f)
                                )
                            }
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                HoverButton(
                    text = "Backup",
                    onClick = {
                        if (SaveGameFile.backup()) {
                            Notifications.success("Backup saved on the backups folder")
                        } else {
                            Notifications.error("Something went wrong while backing up your saved games")
                        }
                    },
                    hoveredColor = BlueLight,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                HoverButton(
                    text = "Save",
                    onClick = {
                        // TODO: Implement save
                    },
                    hoveredColor = YellowLight,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Button(
                    onClick = {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(URI("https://www.buymeacoffee.com/jianastrero"))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Yellow, contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = bmcPainter,
                        contentDescription = "Buy me a coffee",
                        modifier = Modifier.width(100.dp)
                    )
                }
            }
        }
        NavHost(
            startDestination = HLSENav.Main.Empty,
            navController = navController,
            modifier = Modifier.weight(1f)
                .fillMaxHeight()
        ) {
            composable(HLSENav.Main.Empty::class) {
                Spacer(modifier = Modifier)
            }
            composable(HLSENav.Main.PlayerData::class) {
                saveGameFile.saveFileList.firstOrNull { it.characterID == selectedCharacter?.id }
                    ?.characterSaveFileData
                    ?.sqlite
                    ?.let { sqlite ->
                        PersonalDataScreen(
                            sqlite = sqlite,
                            modifier = Modifier.fillMaxSize()
                                .padding(
                                    start = 12.dp,
                                    top = 12.dp,
                                    bottom = 12.dp,
                                    end = 24.dp
                                )
                        )
                    }
                    ?: kotlin.run {
                        Notifications.error("Please don't delete your temp file nerd")
                    }
            }
            composable(HLSENav.Main.Resources::class) {
                saveGameFile.saveFileList.firstOrNull { it.characterID == selectedCharacter?.id }
                    ?.characterSaveFileData
                    ?.sqlite
                    ?.let { sqlite ->
                        ResourcesScreen(
                            sqlite = sqlite,
                            modifier = Modifier.fillMaxSize()
                                .padding(
                                    start = 12.dp,
                                    top = 12.dp,
                                    bottom = 12.dp,
                                    end = 24.dp
                                )
                        )
                    }
                    ?: kotlin.run {
                        Notifications.error("Please don't delete your temp file nerd")
                    }
            }
        }
    }
}