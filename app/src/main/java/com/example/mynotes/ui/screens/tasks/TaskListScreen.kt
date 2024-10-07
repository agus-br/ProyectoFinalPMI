package com.example.mynotes.ui.screens.tasks

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

@Composable
fun TaskListScreen(
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
