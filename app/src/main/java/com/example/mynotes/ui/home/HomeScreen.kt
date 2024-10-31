package com.example.mynotes.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.navigation.NavigationDestination
import com.example.mynotes.ui.notes.NoteListScreen
import com.example.mynotes.ui.tasks.TaskListScreen
import com.example.mynotes.ui.theme.MyNotesTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToNewNote: () -> Unit, // Navegar a agregar una nueva nota
    navigateToUpdateNote: (Int) -> Unit, // Navegar a editar una nota existente
    navigateToNewTask: () -> Unit, // Navegar a agregar una nueva tarea
    navigateToUpdateTask: (Int) -> Unit, // Navegar a editar una tarea existente
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Notes") },
                actions = {
                    IconButton(onClick = navigateToNewNote) {
                        Icon(Icons.Default.Add, contentDescription = "Add Note")
                    }
                    IconButton(onClick = navigateToNewTask) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Título para Notas
                Text(text = "Notes", style = MaterialTheme.typography.titleLarge)
                NoteListScreen(
                    navigateToNewNote = navigateToNewNote,
                    navigateToUpdateNote = navigateToUpdateNote
                )

                // Título para Tareas
                Text(text = "Tasks", style = MaterialTheme.typography.titleLarge)
                TaskListScreen(
                    navigateToNewTask = navigateToNewTask,
                    navigateToUpdateTask = navigateToUpdateTask,
                    onTaskClick = { navigateToUpdateTask(it) },
                    onTaskLongClick = { /* Aquí puedes manejar la acción de eliminar */ },
                    onCheckedChanged = { id, isChecked -> /* Maneja el cambio de estado aquí */ }
                )
            }
        }
    )
}
