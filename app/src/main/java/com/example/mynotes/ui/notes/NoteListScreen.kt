package com.example.mynotes.ui.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.annotation.StringRes
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.navigation.NavigationDestination

// Note List Destination
object NoteListDestination : NavigationDestination {
    override val route = "noteList"
    @StringRes
    override val titleRes = R.string.notes
}

@Composable
fun NoteListScreen(
    navigateToNewNote: () -> Unit,
    navigateToUpdateNote: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val notes = viewModel.notes.collectAsState().value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) { note ->
            NoteItem(
                title = note.title,
                description = note.description,
                onClick = { navigateToUpdateNote(note.id) },
                onLongClick = { viewModel.deleteNote(note) }
            )
        }
    }
}

