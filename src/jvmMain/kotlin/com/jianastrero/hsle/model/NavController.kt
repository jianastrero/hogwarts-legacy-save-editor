package com.jianastrero.hsle.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.jianastrero.hsle.HSLENav

class NavController {
    var onNavigate: (HSLENav) -> Unit by mutableStateOf({})

    fun navigate(route: HSLENav) {
        onNavigate(route)
    }
}