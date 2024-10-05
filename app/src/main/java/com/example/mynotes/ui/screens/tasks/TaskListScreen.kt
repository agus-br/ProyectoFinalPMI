package com.example.mynotes.ui.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.TaskItemData
import com.example.mynotes.ui.components.TaskItem

@Composable
fun TaskListScreen(
    tasks: List<TaskItemData>
) {

    // Uso de LazyColumn para mostrar las tareas en una sola columna
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
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
                onClick = {},
                onLongClick = {},
                onCheckedChange = {}
            )
        }
    }

}
