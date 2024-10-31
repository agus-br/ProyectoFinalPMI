package com.example.mynotes.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.annotation.StringRes
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mynotes.R
import com.example.mynotes.ui.navigation.NavigationDestination
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.ui.AppViewModelProvider

// Task List Destination
object TaskListDestination : NavigationDestination {
    override val route = "taskList"
    @StringRes
    override val titleRes = R.string.tasks
}

@Composable
fun TaskListScreen(
    navigateToNewTask: () -> Unit,
    navigateToUpdateTask: (Int) -> Unit,
    onTaskClick: (Int) -> Unit,
    onTaskLongClick: (Int) -> Unit,
    onCheckedChanged: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskListViewModel = viewModel(factory = AppViewModelProvider.Factory) // Instanciar el ViewModel
) {
    // Observando la lista de tareas desde el ViewModel
    val tasks by viewModel.tasks.collectAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            TaskItem(
                title = task.title,
                description = task.description,
                completed = task.isCompleted,
                onClick = { onTaskClick(task.id) },
                onLongClick = { onTaskLongClick(task.id) },
                onCheckedChange = { isChecked -> onCheckedChanged(task.id, isChecked) }
            )
        }
    }
}
