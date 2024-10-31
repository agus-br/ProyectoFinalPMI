package com.example.mynotes.ui.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.example.mynotes.R
import com.example.mynotes.ui.navigation.NavigationDestination
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.ui.AppViewModelProvider

// Add/Edit Note Destination
object AddEditNoteDestination : NavigationDestination {
    //const val noteIdArg = "noteId"

    // Implementación de las propiedades requeridas por la interfaz
    //override val route = "addEditNote/{$noteIdArg}"

    // Puedes definir un valor de recurso de cadena para el título
    //override val titleRes = R.string.edit_task // Asegúrate de tener este recurso definido

    // Método para construir la ruta con un ID específico
    //fun route(noteId: Int) = "addEditNote/$noteId"

    override val titleRes = R.string.edit_task
    const val noteIdArg = "noteId"
    override val route = "addEditNote/{$noteIdArg}"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddEditScreen(
    noteId: Int? = null,
    viewModel: NoteAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory), // Inyección del ViewModel con Factory
    onNavigateBack: () -> Unit // Callback para regresar a la pantalla anterior
) {
    // Cargar la nota si existe un ID
    LaunchedEffect(noteId) {
        noteId?.let { viewModel.loadNoteTask(it) }
    }

    // Obtener los estados del ViewModel
    val title = viewModel.title.collectAsState()
    val description = viewModel.description.collectAsState()

    // Interfaz de usuario
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (noteId == null) "Add Note" else "Edit Note") },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteNoteTask()
                        onNavigateBack() // Regresar después de eliminar
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Campo para el título de la nota
                TextField(
                    value = title.value,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo para la descripción de la nota
                TextField(
                    value = description.value,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )

                // Botón para guardar la nota
                Button(
                    onClick = {
                        viewModel.saveNoteTask()
                        onNavigateBack() // Regresar después de guardar
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }
            }
        }
    )
}