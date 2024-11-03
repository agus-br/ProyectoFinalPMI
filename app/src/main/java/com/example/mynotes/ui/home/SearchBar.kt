package com.example.mynotes.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun SearchBar(
    placeHolder: String,
    onSearch: (String) -> Unit, // lambda para indicar su acción
    modifier: Modifier = Modifier
) {
    // Estado para almacenar el texto de búsqueda
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it // Actualiza el texto de búsqueda
            onSearch(it) // Llama a la lambda con el texto actualizado
        },
        placeholder = {
            // Texto de marcador de posición
            Text(placeHolder)
        },
        leadingIcon = {
            // Icono de búsqueda
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Ícono de búsqueda",
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MyNotesTheme {
        SearchBar(
            placeHolder = "Buscar",
            onSearch = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}