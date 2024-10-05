package com.example.mynotes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mynotes.ui.components.ControlsBottonBar
import com.example.mynotes.ui.components.MainBottomNavBar
import com.example.mynotes.ui.components.SearchBar
import com.example.mynotes.ui.screens.notes.NoteListScreen

import com.example.mynotes.ui.viewmodel.MyNotesAppViewModel


data class NoteItemData(val title: String, val description: String, val content: String)

val notes = listOf(
    NoteItemData("Título 1", "Breve descripción", "Este es el contenido extenso de la nota..."),
    NoteItemData("Título 2", "Otra descripción", "Otro contenido extenso..."),
    NoteItemData("Título 3", "Otra descripción", "Otro contenido extenso..."),
    NoteItemData("Título 4", "Otra descripción", "Otro contenido extenso..."),
    NoteItemData("Título 5", "Otra descripción", "Otro contenido extenso...")
)



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
        title = {
            Text(
                stringResource(id = currentScreen.title)
            ) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun TopBarWithSearch(
    currentScreen: NoteScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    searchPlaceholder: String,
    searchQuery: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
        NotesAppBar(
            currentScreen = currentScreen,
            canNavigateBack = canNavigateBack,
            navigateUp = navigateUp,
            modifier = Modifier.padding(bottom = 0.dp)
        )
        SearchBar(
            searchPlaceholder,
            searchQuery,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 0.dp)
        )
    }
}

@Composable
fun NotesApp(
    viewModel: MyNotesAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NoteScreens.valueOf(
        backStackEntry?.destination?.route ?: NoteScreens.HomeNotes.name
    )

    Scaffold(
        topBar = {
            TopBarWithSearch(
                currentScreen,
                navController.previousBackStackEntry != null,
                { navController.navigateUp() },
                stringResource(R.string.search_note_placeholder),
                {})
        },
        bottomBar = {
            when (navController.currentDestination?.route) {
                in listOf(NoteScreens.CreateNote.name, NoteScreens.EditNote.name) -> {
                    // Estamos en una pantalla de crear o editar nota
                    ControlsBottonBar(
                    )
                }
                in listOf(NoteScreens.CreateTask.name, NoteScreens.EditTask.name) -> {
                    // Estamos en una pantalla de crear o editar tarea
                    ControlsBottonBar(
                    )
                }
                else -> {
                    // Pantallas de inicio o cualquier otra pantalla
                    MainBottomNavBar(
                        onNotesClick = { navController.navigate(NoteScreens.HomeNotes.name) },
                        onTasksClick = { navController.navigate(NoteScreens.HomeTasks.name) }
                    )
                }
            }
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NoteScreens.HomeNotes.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NoteScreens.HomeNotes.name) {
                NoteListScreen(notes)
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