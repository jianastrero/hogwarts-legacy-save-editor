package com.jianastrero.hsle.component.builder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jianastrero.hsle.HSLENav
import com.jianastrero.hsle.model.NavController

class NavHostBuilder(
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

@Composable
fun rememberNavController(): NavController {
    val navController by remember { mutableStateOf(NavController()) }
    return navController
}