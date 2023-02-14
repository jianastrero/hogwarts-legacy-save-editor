package com.jianastrero.hsle.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.extensions.getGenericValue
import com.jianastrero.hsle.model.Field
import com.jianastrero.hsle.theme.BlueGray
import com.jianastrero.hsle.theme.Yellow

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

@Composable
fun <T> TextFieldFieldItem(
    item: Field<out T>,
    onUpdate: (Field<out T>) -> Unit,
    onUpdatesqlite: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = item.value.toString(),
        onValueChange = {
            onUpdate(item.copy(it.getGenericValue(item.value)))
        },
        label = {
            Text(item.title)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = BlueGray.copy(alpha = 0.6f),
            cursorColor = Color.White,
            focusedLabelColor = Color.White.copy(alpha = 0.6f),
            unfocusedLabelColor = Color.White.copy(alpha = 0.4f),
            focusedIndicatorColor = Yellow,
            unfocusedIndicatorColor = Yellow.copy(alpha = 0.3f)
        ),
        modifier = Modifier
            .onFocusChanged {
                onUpdatesqlite()
            }
            .then(modifier)
    )
}

@Composable
@Preview
private fun TextFieldFieldItemPreview() {
    TextFieldFieldItem(
        item = Field.PersonalDataField.FirstName("Jian"),
        onUpdate = {},
        onUpdatesqlite = {},
        modifier = Modifier.fillMaxWidth()
    )
}