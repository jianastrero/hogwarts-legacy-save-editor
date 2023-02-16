package com.jianastrero.hsle.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import com.jianastrero.hsle.extensions.getGenericValue
import com.jianastrero.hsle.model.Field
import com.jianastrero.hsle.theme.BlueGray
import com.jianastrero.hsle.theme.Yellow


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