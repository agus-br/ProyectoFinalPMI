package com.example.mynotes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.mynotes.ui.viewmodel.NotesAppViewModel

enum class NoteScreens(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    CreateNote(title = R.string.create_note),
    EditNote(title = R.string.edit_note),
    NoteDetail(title = R.string.note_detail)
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