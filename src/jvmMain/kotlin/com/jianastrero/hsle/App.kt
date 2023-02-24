package com.jianastrero.hsle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.component.NavHost
import com.jianastrero.hsle.component.NotificationItem
import com.jianastrero.hsle.component.rememberNavController
import com.jianastrero.hsle.nav.HLSENav
import com.jianastrero.hsle.notification.Notifications
import com.jianastrero.hsle.screen.InitialScreen
import com.jianastrero.hsle.screen.MainScreen
import com.jianastrero.hsle.theme.HLSETheme
import com.jianastrero.hsle.theme.Red
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App(
    saveGameFile: SaveGameFile,
    onClose: () -> Unit
) {
    val backgroundPainter = painterResource("background.jpg")
    val titlePainter = painterResource("title.png")
    val navController = rememberNavController()

    HLSETheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = backgroundPainter,
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
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
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onClose)
                            .padding(6.dp)
                            .background(Red, shape = CircleShape)
                            .padding(8.dp)
                            .shadow(8.dp, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close HLSE",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                NavHost(
                    startDestination = HLSENav.LoadingScreen,
                    navController = navController,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    composable(HLSENav.LoadingScreen::class) {
                        InitialScreen(modifier = Modifier.fillMaxSize())
                    }

                    composable(HLSENav.MainScreen::class) {
                        MainScreen(
                            saveGameFile = saveGameFile,
                            modifier = Modifier.fillMaxSize()
                        )
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

    LaunchedEffect(saveGameFile) {
        delay(5_000)
        navController.navigate(HLSENav.MainScreen)
    }
}