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
import com.example.mynotes.data.model.Task
import com.example.mynotes.ui.components.TaskItem
import androidx.annotation.StringRes
import com.example.mynotes.R
import com.example.mynotes.ui.navigation.NavigationDestination

// Task List Destination
object TaskListDestination : NavigationDestination {
    override val route = "taskList"
    @StringRes
    override val titleRes = R.string.tasks
}

@Composable
fun TaskListScreen(
    navigateToNewTask: () -> Unit, // Navegar a agregar una nota o tarea
    navigateToUpdateTask: (Int) -> Unit, // Navegar a editar un elemento existente
    tasks: List<Task>,
    onTaksClick: () -> Unit,
    onTaskLongClick: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit
) {

    // Uso de LazyColumn para mostrar las tareas en una sola columna
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
                content = task.content,
                state = task.state,
                completed = task.completed,
                onClick = onTaksClick,
                onLongClick = onTaskLongClick,
                onCheckedChange = onCheckedChanged
            )
        }
    }

}
