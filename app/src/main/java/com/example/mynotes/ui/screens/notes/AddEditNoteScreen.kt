package com.example.mynotes.ui.screens.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.R
import com.example.mynotes.data.model.Note
import com.example.mynotes.data.notes
import com.example.mynotes.ui.theme.MyNotesTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    note: Note?,
    modifier: Modifier = Modifier
) {
    // Estado para almacenar el texto de los campos
    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Título de la nota
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = {
                Text(stringResource(R.string.title))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )

        // Descripción de la nota
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = {
                Text(stringResource(R.string.description))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )

        // Contenido de la nota
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            placeholder = {
                Text(stringResource(R.string.content))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        AddEditNoteScreen(
            note = notes[0],
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}