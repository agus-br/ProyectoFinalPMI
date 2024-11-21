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
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.R
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskType
import com.example.mynotes.ui.home.HomeViewModel
import com.example.mynotes.ui.theme.MyNotesTheme

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
        items(items = taskList, key = { it.id }) { task ->
            TaskItem(
                task = task,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { taskItemOnclick(task) } // Aquí se pasa el note
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    MyNotesTheme {
        TaskList(
            taskList = listOf(
                NoteTask(
                    id = 0,
                    title = "Título de un nota",
                    description = "Hola",
                    type = NoteTaskType.TASK,
                    isCompleted = false,
                    dueDate = 121245
                ),
                NoteTask(
                    id = 1,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                            "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                            "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                            "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                            "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                            "todo en paz. ",
                    type = NoteTaskType.TASK,
                    isCompleted = false,
                    dueDate = 121245
                ),
                NoteTask(
                    id = 2,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre.",
                    type = NoteTaskType.TASK,
                    isCompleted = false,
                    dueDate = 121245
                ),
                NoteTask(
                    id = 3,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                            "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                            "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                            "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                            "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                            "todo en paz. ",
                    type = NoteTaskType.TASK,
                    isCompleted = false,
                    dueDate = 121245
                ),
                NoteTask(
                    id = 4,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                            "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                            "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                            "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                            "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                            "todo en paz. ",
                    type = NoteTaskType.TASK,
                    isCompleted = false,
                    dueDate = 121245
                ),
                NoteTask(
                    id = 5,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                            "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                            "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                            "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                            "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                            "todo en paz. ",
                    type = NoteTaskType.TASK,
                    isCompleted = false,
                    dueDate = 121245
                )
            ),
            taskItemOnclick = {},
            contentPadding = PaddingValues()
        )
    }
}
*/