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
import com.example.mynotes.ui.notes.NoteEntryDestination
import com.example.mynotes.ui.notes.NoteEntryScreen
import com.example.mynotes.ui.settings.SettingDestination
import com.example.mynotes.ui.settings.SettingScreen
import com.example.mynotes.ui.tasks.AddEditTaskDestination
import com.example.mynotes.ui.tasks.TaskAddEditScreen
import com.example.mynotes.ui.tasks.TaskEntryDestination
import com.example.mynotes.ui.tasks.TaskEntryScreen

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
                onClickActionSettings = { navController.navigate(SettingDestination.route) },
                navigateToNewNote = { navController.navigate(NoteEntryDestination.route)},
                navigateToUpdateNote = { navController.navigate("${AddEditNoteDestination.route}/${it}") },
                navigateToNewTask = { navController.navigate(TaskEntryDestination.route) },
                navigateToUpdateTask = { navController.navigate("${AddEditTaskDestination.route}/${it}") }
            )
        }

        composable(route = SettingDestination.route) {
            SettingScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = NoteEntryDestination.route) {
            NoteEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // Pantalla para editar notas
        composable(
            route = AddEditNoteDestination.routeWithArgs,
            arguments = listOf(navArgument(AddEditNoteDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteAddEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = TaskEntryDestination.route) {
            TaskEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // Pantalla para añadir o editar notas
        composable(
            route = AddEditTaskDestination.routeWithArgs,
            arguments = listOf(navArgument(AddEditTaskDestination.taskIdArg) {
                type = NavType.IntType
            })
        ) {
            TaskAddEditScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
