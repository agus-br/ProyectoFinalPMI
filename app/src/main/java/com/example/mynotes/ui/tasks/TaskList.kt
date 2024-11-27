package com.example.mynotes.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.dimensionResource
import com.example.mynotes.R
import com.example.mynotes.data.NoteTask

@Composable
fun TaskList(
    taskList: List<NoteTask>,
    taskItemOnclick: (NoteTask) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = taskList,
            key = { it.id }
        ) { task ->
            TaskItem(
                task = task,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { taskItemOnclick(task) } // Aquí se pasa el item
            )
        }
    }
}
