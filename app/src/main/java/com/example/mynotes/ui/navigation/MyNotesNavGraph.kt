package com.example.mynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotes.ui.home.HomeDestination
import com.example.mynotes.ui.home.HomeScreen
import com.example.mynotes.ui.notes.AddEditNoteDestination
import com.example.mynotes.ui.notes.NoteAddEditScreen
import com.example.mynotes.ui.tasks.AddEditTaskDestination
import com.example.mynotes.ui.notes.NoteListDestination
import com.example.mynotes.ui.tasks.TaskAddEditScreen
import com.example.mynotes.ui.tasks.TaskListDestination

@Composable
fun MyNotesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route, // Pantalla inicial
        modifier = modifier
    ) {
        // Pantalla de inicio
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToNewNote = { navController.navigate("${AddEditNoteDestination.route}/${it}")},
                navigateToUpdateNote = { navController.navigate("${AddEditNoteDestination.route}/${it}") },
                navigateToNewTask = { navController.navigate("${AddEditTaskDestination.route}/${it}") },
                navigateToUpdateTask = { navController.navigate("${AddEditTaskDestination.route}/${it}") }
            )
        }
/*
        // Pantalla de lista de notas
        composable(route = NoteListDestination.route) {
            NoteListScreen(
                navigateToNewNote = { noteId ->  navController.navigate(AddEditNoteDestination.route(noteId)) },
                navigateToUpdateNote = { navController.navigate(TaskListDestination.route) }
            )
        }
*/
        // Pantalla para añadir o editar notas
        composable(
            route = AddEditNoteDestination.routeWithArgs,
            arguments = listOf(navArgument(AddEditNoteDestination.noteIdArg) { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt(AddEditNoteDestination.noteIdArg) ?: -1 // -1 para nueva nota
            NoteAddEditScreen(
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
/*
        // Pantalla de lista de tareas
        composable(route = TaskListDestination.route) {
            TaskListScreen(
                navigateToNewTask = { taskId -> navController.navigate(AddEditTaskDestination.route(taskId)) },
                navigateToUpdateTask = { navController.navigate(NoteListDestination.route) })
        }
*/
        // Pantalla para añadir o editar tareas
        composable(
            route = AddEditTaskDestination.routeWithArgs,
            arguments = listOf(navArgument(AddEditTaskDestination.taskIdArg) { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(AddEditTaskDestination.taskIdArg) ?: -1 // -1 para nueva tarea
            TaskAddEditScreen(
                taskId = taskId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
