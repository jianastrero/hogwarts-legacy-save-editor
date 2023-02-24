package com.jianastrero.hsle.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.component.LoadablePage
import com.jianastrero.hsle.component.TextFieldFieldItem
import com.jianastrero.hsle.enumerations.HouseCrest
import com.jianastrero.hsle.model.Field
import com.jianastrero.hsle.sqlite.SQLite
import com.jianastrero.hsle.viewmodel.FieldViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PersonalDataScreen(
    sqlite: SQLite,
    modifier: Modifier = Modifier
) {
    val viewModel by remember {
        mutableStateOf(
            FieldViewModel(
                sqlite = sqlite,
                list = listOf(
                    Field.PersonalDataField.FirstName(""),
                    Field.PersonalDataField.LastName(""),
                    Field.PersonalDataField.Experience(0),
                    Field.PersonalDataField.Galleons(0),
                    Field.PersonalDataField.TalentPoints(0),
                    Field.PersonalDataField.House("")
                )
            )
        )
    }
    val defaultScope = rememberCoroutineScope { Dispatchers.Default }
    val ioScope = rememberCoroutineScope { Dispatchers.IO }
    val userHouse by derivedStateOf { viewModel.state.fields.firstOrNull { it is Field.PersonalDataField.House } }

    LoadablePage(state = viewModel.state.loadablePageState) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            items(viewModel.state.fields.filterNot { it is Field.PersonalDataField.House }) {
                TextFieldFieldItem(
                    it,
                    onUpdate = {
                        defaultScope.launch {
                            viewModel.updateField(it)
                        }
                    },
                    onUpdatesqlite = {
                        ioScope.launch {
                            viewModel.updateFieldSqlite(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item(
                span = { GridItemSpan(2) }
            ) {
                Spacer(modifier = Modifier.height(0.dp))
            }
            itemsIndexed(HouseCrest.values()) {i, item ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                            .background(
                                if (item.value == userHouse?.value) {
                                    Color.White.copy(alpha = 0.8f)
                                } else {
                                    Color.White.copy(alpha = 0.2f)
                                }
                            )
                            .align(if (i % 2 == 0) Alignment.CenterEnd else Alignment.CenterStart)
                            .clickable {
                                ioScope.launch {
                                    userHouse?.let {
                                        val newField = it.copy(newValue = item.value)
                                        viewModel.updateFieldSqlite(newField)
                                        viewModel.updateField(newField)
                                    }
                                }
                            }
                            .padding(12.dp)
                    ) {
                        Image(
                            imageVector = item.imageVector,
                            contentDescription = item.value,
                            colorFilter = ColorFilter.tint(
                                if (item.value == userHouse?.value) {
                                    item.color.copy(alpha = 0.8f)
                                } else {
                                    Color.Black.copy(alpha = 0.6f)
                                }
                            ),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
            }
        }
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