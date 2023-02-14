package com.jianastrero.hsle.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.jianastrero.hsle.enumerations.LoadablePageState
import com.jianastrero.hsle.model.Field
import com.jianastrero.hsle.sqlite.HLSESQLite
import com.jianastrero.hsle.state.FieldState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FieldViewModel<T>(list: List<Field<out T>>) {

    private var _state by mutableStateOf(FieldState(list))
    val state: FieldState<T>
        get() = _state

    private fun updateState(
        fields: List<Field<out T>> = state.fields,
        loadablePageState: LoadablePageState = state.loadablePageState
    ) {
        _state = _state.copy(
            fields = fields,
            loadablePageState = loadablePageState
        )
    }

    suspend fun fetch() = withContext(Dispatchers.IO) {
        try {
            updateState(loadablePageState = LoadablePageState.Loading)

            val hlseSqlite = HLSESQLite.getInstance()
            val fields = hlseSqlite?.fetchAll(state.fields) ?: state.fields

            updateState(
                loadablePageState = LoadablePageState.Loaded,
                fields = fields
            )
        } catch (e: Exception) {
            e.printStackTrace()
            updateState(loadablePageState = LoadablePageState.Error(e.message ?: "Something went wrong"))
            // TODO: Handle errors
        }
    }

    suspend fun updateField(field: Field<out T>) = withContext(Dispatchers.Default) {
        val newFields = state.fields.map {
            if (it.title == field.title) {
                field
            } else {
                it
            }
        }
        updateState(fields = newFields)
    }
}