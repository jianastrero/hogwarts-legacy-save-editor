package com.jianastrero.hsle.enumerations

sealed class LoadablePageState {
    object Initial : LoadablePageState()
    object Loading : LoadablePageState()
    object Loaded : LoadablePageState()
    object Saving : LoadablePageState()
}
