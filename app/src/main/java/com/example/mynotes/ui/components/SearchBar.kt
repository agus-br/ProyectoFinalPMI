package com.example.mynotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search


@Composable
fun SearchBar(
    placeHolder: String,
    onSearch: (String) -> Unit // lambda para indicar su acción
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
        modifier = Modifier.fillMaxWidth(), // Se expande para llenar el ancho
    )
}
