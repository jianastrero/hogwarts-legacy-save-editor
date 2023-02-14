package com.jianastrero.hsle.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jianastrero.hsle.HSLENav

class NavController {
    private var onNavigate: (HSLENav) -> Unit = {}

    fun navigate(route: HSLENav) {
        onNavigate(route)
    }

    class Builder(
        private val startDestination: HSLENav,
        private val navController: NavController
    ) {
        private var _destinations: Map<HSLENav, @Composable () -> Unit> by mutableStateOf(mapOf())
        private var _currentDestination by mutableStateOf(startDestination)
        val currentBlock by derivedStateOf { _destinations[_currentDestination] }

        init {
            navController.onNavigate = { _currentDestination = it }
        }

        fun composable(
            route: HSLENav,
            block: @Composable () -> Unit
        ) {
            val newDestinations = _destinations.toMutableMap()
            newDestinations[route] = block
            _destinations = newDestinations.toMap()
        }
    }
}

@Composable
fun rememberNavController(): NavController {
    val navController by remember { mutableStateOf(NavController()) }
    return navController
}