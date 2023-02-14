package com.jianastrero.hsle.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.HLSENav
import com.jianastrero.hsle.model.NavController
import com.jianastrero.hsle.model.rememberNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHost(
    startDestination: HLSENav,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    block: NavController.Builder.() -> Unit
) {

    val builder by remember { mutableStateOf(NavController.Builder(startDestination, navController)) }
    val currentBlock by derivedStateOf { builder.currentBlock }

    LaunchedEffect(true) {
        block(builder)
    }

    Box(modifier = modifier) {
        AnimatedContent(
            currentBlock,
            transitionSpec = {
                fadeIn() with fadeOut()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            currentBlock?.invoke()
        }
    }
}