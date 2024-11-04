package com.example.mynotes.ui.notes

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.components.ActionBottonBar
import com.example.mynotes.ui.components.ActionTopNavBar
import com.example.mynotes.ui.navigation.NavigationDestination
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.launch

object AddEditNoteDestination : NavigationDestination {
    override val route = "note_edit"
    override val titleRes = R.string.edit_note
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ActionTopNavBar(
                title = stringResource(AddEditNoteDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                enableActionButtons = true,
                onSetReminder = {}
            )
        },
        bottomBar = {
            ActionBottonBar(
                title = viewModel.noteUiState.noteDetails.type.toString(),
                onActionLeft = {},
                onActionRight = {}
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NoteEntryBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
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
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteAddEditScreenPreview() {
    MyNotesTheme() {
        NoteAddEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}