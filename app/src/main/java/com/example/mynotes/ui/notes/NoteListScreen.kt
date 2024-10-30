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
import com.example.mynotes.data.model.Note
import com.example.mynotes.ui.components.NoteItem
import androidx.annotation.StringRes
import com.example.mynotes.R
import com.example.mynotes.ui.navigation.NavigationDestination

// Note List Destination
object NoteListDestination : NavigationDestination {
    override val route = "noteList"
    @StringRes
    override val titleRes = R.string.notes
}

@Composable
fun NoteListScreen(
    navigateToNewNote: () -> Unit, // Navegar a agregar una nota o tarea
    navigateToUpdateNote: (Int) -> Unit, // Navegar a editar un elemento existente
    notes: List<Note>,
    onNoteClick: () -> Unit,
    onNoteLongClick: () -> Unit
) {

    // Uso de LazyVerticalGrid para mostrar las notas en dos columnas
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Definir que serán 2 columnas
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
                content = note.content,
                onClick = onNoteClick,
                onLongClick = onNoteLongClick
            )
        }
    }

}
