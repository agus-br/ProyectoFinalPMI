package com.example.mynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotes.ui.home.HomeDestination
import com.example.mynotes.ui.home.HomeScreen
import com.example.mynotes.ui.notes.NoteListDestination
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
                navigateToNewNoteTask = { navController.navigate(NoteListDestination.route) },
                navigateToUpdateNoteTask = { navController.navigate(TaskListDestination.route) }
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

        // Pantalla para añadir o editar notas
        composable(
            route = AddEditNoteDestination.routeWithArgs,
            arguments = listOf(navArgument(AddEditNoteDestination.noteIdArg) { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt(AddEditNoteDestination.noteIdArg) ?: -1 // -1 para nueva nota
            AddEditNoteScreen(
                noteId = noteId,
                navigateBack = { navController.popBackStack() }
            )
        }

        // Pantalla de lista de tareas
        composable(route = TaskListDestination.route) {
            TaskListScreen(
                navigateToNewTask = { taskId -> navController.navigate(AddEditTaskDestination.route(taskId)) },
                navigateToUpdateTask = { navController.navigate(NoteListDestination.route) })
        }

        // Pantalla para añadir o editar tareas
        composable(
            route = AddEditTaskDestination.routeWithArgs,
            arguments = listOf(navArgument(AddEditTaskDestination.taskIdArg) { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(AddEditTaskDestination.taskIdArg) ?: -1 // -1 para nueva tarea
            AddEditTaskScreen(
                taskId = taskId,
                navigateBack = { navController.popBackStack() }
            )
        }*/
    }
}
