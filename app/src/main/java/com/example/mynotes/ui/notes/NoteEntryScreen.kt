// Archivo NoteEntryScreen.kt

package com.example.mynotes.ui.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.MyNotesTopAppBar
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.components.ActionBar
import com.example.mynotes.ui.components.BottomActionBarModal
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.launch

object NoteEntryDestination {
    const val route = "new_note"
    val titleRes = R.string.add_note
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MyNotesTopAppBar(
                title = stringResource(R.string.add_note),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                onClickActionSettings = {}
            )
        },
        bottomBar = {
            BottomActionBarModal(
                onDeleteClick = {},
                onSelectImage = {},
                onTakePhoto = {},
                onTakeAudio = {},
                onTakeVideo = {},
                content = viewModel.noteUiState.noteDetails.type.toString(),
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NoteEntryBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveNote()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun NoteEntryBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        NoteInputForm(
            noteDetails = noteUiState.noteDetails,
            onValueChange = onNoteValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = noteUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

@Composable
fun NoteInputForm(
    noteDetails: NoteDetails,
    modifier: Modifier = Modifier,
    onValueChange: (NoteDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = noteDetails.title,
            onValueChange = { onValueChange(noteDetails.copy(title = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = noteDetails.description,
            onValueChange = { onValueChange(noteDetails.copy(description = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        if (enabled) {
            Text(
                //text = stringResource(R.string.required_fields),
                text = "Campos requeridos",
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteEntryBodyPreview() {
    MyNotesTheme {
        NoteEntryBody(
            noteUiState = NoteUiState(
                NoteDetails(
                    id = 1,
                    title = "Note Title",
                    description = "Note content here"
                )
            ),
            onNoteValueChange = {},
            onSaveClick = {}
        )
    }
}
