package com.example.mynotes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.mynotes.data.DataSource
import com.example.mynotes.data.OrderUiState
import com.example.mynotes.ui.NotesAppViewModel
import com.example.mynotes.ui.StartNotesScreen
import com.example.mynotes.ui.NoteScreen
import com.example.mynotes.ui.TaskScreen

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