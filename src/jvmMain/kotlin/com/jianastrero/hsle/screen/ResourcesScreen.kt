package com.jianastrero.hsle.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jianastrero.hsle.component.LoadablePage
import com.jianastrero.hsle.model.Field
import com.jianastrero.hsle.sqlite.SQLite
import com.jianastrero.hsle.viewmodel.FieldViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ResourcesScreen(
    sqlite: SQLite,
    modifier: Modifier = Modifier
) {
    val viewModel by remember {
        mutableStateOf(
            FieldViewModel(
                sqlite = sqlite,
                list = Field.ResourcesField.values()
            )
        )
    }
    val defaultScope = rememberCoroutineScope { Dispatchers.Default }
    val ioScope = rememberCoroutineScope { Dispatchers.IO }

    LoadablePage(state = viewModel.state.loadablePageState) {
        MultipleTextFieldScreen(
            fields = viewModel.state.fields,
            onUpdateField = {
                defaultScope.launch {
                    viewModel.updateField(it)
                }
            },
            onUpdateFieldSqlite = {
                ioScope.launch {
                    viewModel.updateFieldSqlite(it)
                }
            },
            modifier = modifier,
            rows = 2
        )
    }

    LaunchedEffect(true)  {
        viewModel.fetch()
    }
}

@Composable
@Preview
private fun PersonalDataScreenPreview() {
    PersonalDataScreen(
        sqlite = SQLite.None,
        modifier = Modifier.fillMaxWidth()
    )
}