package com.example.mynotes

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api

import com.example.mynotes.ui.viewmodel.MyNoteAppViewModel

enum class NoteScreens(@StringRes val title: Int) {
    HomeNotes(title = R.string.notes),  // Pantalla principal donde se muestran todas las notas
    CreateNote(title = R.string.create_note),  // Pantalla para crear una nueva nota
    EditNote(title = R.string.edit_note),  // Pantalla para editar una nota existente
    HomeTasks(title = R.string.notes),  // Pantalla principal donde se muestran todas las tareas
    CreateTask(title = R.string.create_task),  // Pantalla para crear una nueva tarea
    EditTask(title = R.string.edit_task),  // Pantalla para editar una tarea existente
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    currentScreen: NoteScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun NotesApp(
    viewModel: MyNoteAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

}