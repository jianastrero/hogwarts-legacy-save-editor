package com.jianastrero.hsle.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.component.TextFieldFieldItem
import com.jianastrero.hsle.model.Field

@Composable
fun <T> MultipleTextFieldScreen(
    fields: List<Field<out T>>,
    onUpdateField: (Field<out T>) -> Unit,
    onUpdateFieldSqlite: (Field<out T>) -> Unit,
    modifier: Modifier = Modifier,
    rows: Int = 1
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(rows),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(fields) {
            TextFieldFieldItem(
                it,
                onUpdate = onUpdateField,
                onUpdatesqlite = {
                    onUpdateFieldSqlite(it)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
