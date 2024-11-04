package com.example.mynotes.ui.tasks

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mynotes.ui.navigation.NavigationDestination
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.components.ActionBottonBar
import com.example.mynotes.ui.components.ActionTopNavBar
import com.example.mynotes.ui.notes.AddEditNoteDestination
import com.example.mynotes.ui.notes.NoteAddEditScreen
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.launch

// Add/Edit Note Destination
object AddEditTaskDestination : NavigationDestination {
    override val titleRes = R.string.edit_task
    override val route = "task_edit"
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditScreen(
    onNavigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory) // Inyección del ViewModel con Factory
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ActionTopNavBar(
                title = stringResource(R.string.add_task),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                enableActionButtons = true,
                onSetReminder = {}
            )
        },
        bottomBar = {
            ActionBottonBar(
                title = viewModel.taskUiState.taskDetails.type.toString(),
                onActionLeft = {},
                onActionRight = {}
            )
        },
        modifier = modifier
    ) { innerPadding ->
        TaskEntryBody(
            taskUiState = viewModel.taskUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
                    onNavigateBack()
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
fun TaskAddEditScreenPreview() {
    MyNotesTheme() {
        NoteAddEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}