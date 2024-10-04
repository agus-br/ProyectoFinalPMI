package com.example.mynotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


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
        leadingIcon = {
            // Icono de búsqueda
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar",
                tint = Color.Gray // Color del icono
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
