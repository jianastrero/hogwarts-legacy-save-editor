package com.jianastrero.hsle.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.nav.HLSENav
import kotlin.reflect.KClass

@Composable
fun rememberNavController(): NavHost.NavController {
    val navController: NavHost.NavController by remember { mutableStateOf(NavHost.NavController()) }
    return navController
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHost(
    startDestination: HLSENav,
    modifier: Modifier = Modifier,
    navController: NavHost.NavController = rememberNavController(),
    block: NavHost.() -> Unit
) {
    val navHost by remember {
        mutableStateOf(
            NavHost(startDestination).apply(block).apply {
                this.navController = navController
                this.navController.navHost(this)
            }
        )
    }

    Box(modifier = modifier) {
        AnimatedContent(
            targetState = navHost.currentRoute,
            transitionSpec = {
                val tweenSpecFade = tween<Float>(500, 0, FastOutSlowInEasing)
                fadeIn(tweenSpecFade) with fadeOut(tweenSpecFade)
            }
        ) {
            navHost.currentComposable()
        }
    }
}

class NavHost(startDestination: HLSENav) {
    private val composables: MutableList<ComposableItem> = mutableListOf()
    var currentRoute: HLSENav by mutableStateOf(startDestination)
    var navController: NavController = NavController()

    val currentComposable: @Composable () -> Unit by derivedStateOf {
        {
            composables.firstOrNull {
                it.route.qualifiedName == currentRoute::class.qualifiedName
            }?.composable
                ?.invoke(currentRoute)
        }
    }

    fun composable(
        route: KClass<*>,
        composable: @Composable (payload: HLSENav) -> Unit
    ) {
        composables.add(ComposableItem(route, composable))
    }

    data class ComposableItem(
        val route: KClass<*>,
        val composable: @Composable (HLSENav) -> Unit
    ) {
        @Composable
        operator fun invoke(payload: HLSENav) {
            composable(payload)
        }
    }

    class NavController {
        private var onNavigate: (HLSENav) -> Unit = {}

        internal fun navHost(navHost: NavHost) {
            onNavigate = { navHost.currentRoute = it }
        }

        fun navigate(route: HLSENav) {
            onNavigate(route)
        }
    }
}