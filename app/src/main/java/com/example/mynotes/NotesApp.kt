package com.example.mynotes

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.mynotes.ui.viewmodel.NotesAppViewModel

enum class NoteScreens(@StringRes val title: Int) {
    HomeNotes(title = R.string.notes),  // Pantalla principal donde se muestran todas las notas
    CreateNote(title = R.string.create_note),  // Pantalla para crear una nueva nota
    EditNote(title = R.string.edit_note),  // Pantalla para editar una nota existente
    HomeTasks(title = R.string.notes),  // Pantalla principal donde se muestran todas las tareas
    CreateTask(title = R.string.create_task),  // Pantalla para crear una nueva tarea
    EditTask(title = R.string.edit_task),  // Pantalla para editar una tarea existente
}


@Composable
fun NotesAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Composable
fun NotesApp(
    viewModel: NotesAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

}