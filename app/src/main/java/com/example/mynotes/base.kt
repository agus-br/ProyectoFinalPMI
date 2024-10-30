package com.example.mynotes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mynotes.ui.components.ControlsBottonBar
import com.example.mynotes.ui.components.MainBottomNavBar
import com.example.mynotes.ui.components.SearchBar
import com.example.mynotes.ui.notes.NoteListScreen

import com.example.mynotes.ui.viewmodel.MyNotesAppViewModel

// Importación de los datos de ejemplo
import com.example.mynotes.data.notes
import com.example.mynotes.data.tasks
import com.example.mynotes.ui.notes.AddEditNoteScreen
import com.example.mynotes.ui.tasks.TaskAddEditScreen
import com.example.mynotes.ui.tasks.TaskListScreen

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
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            when (navController.currentDestination?.route) {
                in listOf(NoteScreens.CreateNote.name, NoteScreens.EditNote.name) -> {
                    NotesAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                }
                in listOf(NoteScreens.CreateTask.name, NoteScreens.EditTask.name) -> {
                    NotesAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                }
                else -> {
                    TopBarWithSearch(
                        currentScreen,
                        navController.previousBackStackEntry != null,
                        { navController.navigateUp() },
                        stringResource(R.string.search_note_placeholder),
                        {})
                }
            }
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
                        onTasksClick = { navController.navigate(NoteScreens.HomeTasks.name) },
                        onNewNoteClick = { navController.navigate(NoteScreens.CreateNote.name) }
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
                NoteListScreen(
                    notes = notes,
                    onNoteClick = {
                        navController.navigate(NoteScreens.EditNote.name)
                    },
                    onNoteLongClick = {})
            }
            composable(route = NoteScreens.CreateNote.name) {
                AddEditNoteScreen(
                    note = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = NoteScreens.EditNote.name) {
                AddEditNoteScreen(
                    note = notes[selectedItem],
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = NoteScreens.HomeTasks.name) {
                TaskListScreen(
                    tasks = tasks,
                    onTaksClick = {
                        navController.navigate(NoteScreens.EditTask.name)
                    },
                    onTaskLongClick = {},
                    onCheckedChanged = {}
                )
            }
            composable(route = NoteScreens.CreateTask.name) {
                TaskAddEditScreen(
                    task = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = NoteScreens.EditTask.name) {
                TaskAddEditScreen(
                    task = tasks[selectedItem],
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}