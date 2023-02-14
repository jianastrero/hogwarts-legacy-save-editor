package com.jianastrero.hsle.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.component.HLSEButton
import com.jianastrero.hsle.component.NavHost
import com.jianastrero.hsle.model.HLSaveFileData
import com.jianastrero.hsle.model.rememberNavController
import com.jianastrero.hsle.nav.HLSENav
import com.jianastrero.hsle.theme.Yellow
import java.awt.Desktop
import java.net.URI

@Composable
fun MainScreen(
    hlSaveFileData: HLSaveFileData,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val bmcPainter = painterResource("bmc.svg")

    Row(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
                .padding(horizontal = 12.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(HLSENav.Main.values()) {
                    HLSEButton(
                        text = it.title,
                        onClick = {

                        },
                        modifier = Modifier
                    )
                }
            }
            Button(
                onClick = {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(URI("https://www.buymeacoffee.com/jianastrero"))
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Yellow, contentColor = Color.Black),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(12.dp)
                    .width(140.dp)
                    .align(Alignment.Start)
            ) {
                Image(
                    painter = bmcPainter,
                    contentDescription = "Buy me a coffee",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        NavHost(
            startDestination = HLSENav.Main.PlayerData,
            navController = navController,
            modifier = Modifier.weight(1f)
                .fillMaxHeight()
        ) {

        }
    }
}