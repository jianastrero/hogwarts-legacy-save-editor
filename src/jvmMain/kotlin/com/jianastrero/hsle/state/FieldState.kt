package com.jianastrero.hsle.state

import com.jianastrero.hsle.enumerations.LoadablePageState
import com.jianastrero.hsle.model.Field

data class FieldState<T>(
    val fields: List<Field<out T>>,
    val loadablePageState: LoadablePageState = LoadablePageState.Initial
)