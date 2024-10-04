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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

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
    viewModel: NotesAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NoteScreens.valueOf(
        backStackEntry?.destination?.route ?: NoteScreens.HomeNotes.name
    )

    Scaffold(
        topBar = {
            NotesAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NoteScreens.HomeNotes.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NoteScreens.HomeNotes.name) {
                // Aquí cargarás tu pantalla de HomeNotes
            }
            composable(route = NoteScreens.CreateNote.name) {
                // Pantalla de creación de nota
            }
            composable(route = NoteScreens.EditNote.name) {
                // Pantalla de edición de nota
            }
            composable(route = NoteScreens.HomeTasks.name) {
                // Pantalla principal para las tareas
            }
            composable(route = NoteScreens.CreateTask.name) {
                // Pantalla para crear una nueva tarea
            }
            composable(route = NoteScreens.EditTask.name) {
                // Pantalla de edición de tareas
            }
        }
    }
}