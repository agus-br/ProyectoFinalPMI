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
import com.example.mynotes.ui.notes.NoteTestDestination
import com.example.mynotes.ui.notes.NoteTestScreen
import com.example.mynotes.ui.settings.SettingDestination
import com.example.mynotes.ui.settings.SettingsScreen
import com.example.mynotes.ui.tasks.AddEditTaskDestination
import com.example.mynotes.ui.tasks.AddEditTaskScreen

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
                navigateToNewNote = { navController.navigate("${NoteTestDestination.route}/0")},
                navigateToUpdateNote = { navController.navigate("${NoteTestDestination.route}/$it") },
                navigateToNewTask = { navController.navigate("${AddEditTaskDestination.route}/0") },
                navigateToUpdateTask = { navController.navigate("${AddEditTaskDestination.route}/$it") }
            )
        }

        composable(route = SettingDestination.route) {
            SettingsScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(
            route = "${NoteTestDestination.route}/{${NoteTestDestination.noteIdArg}}",
            arguments = listOf(
                navArgument(NoteTestDestination.noteIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt(NoteTestDestination.noteIdArg)
            NoteTestScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = "${AddEditTaskDestination.route}/{${AddEditTaskDestination.taskIdArg}}",
            arguments = listOf(
                navArgument(AddEditTaskDestination.taskIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(AddEditTaskDestination.taskIdArg)
            AddEditTaskScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }


    }
}
