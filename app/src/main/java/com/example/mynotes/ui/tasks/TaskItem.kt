package com.example.mynotes.ui.tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskType
import com.example.mynotes.ui.notes.NoteItem
import com.example.mynotes.ui.theme.MyNotesTheme
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    task: NoteTask,
    modifier: Modifier = Modifier
) {

    var checked by remember { mutableStateOf(task.isCompleted) }

    // Contenedor que ajusta el tamaño vertical según el contenido
    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupar todo el ancho
            .wrapContentHeight(), // Altura mínima y máxima
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row( // Usar Row para organizar el checkbox al lado izquierdo de la tarea
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Espaciado interno
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Checkbox para marcar como completada o no
            Checkbox(
                checked = checked, // Estado del checkbox
                onCheckedChange = {
                    if (checked) checked = false
                    else checked = true
                } // Callback cuando cambia el estado
            )

            // Columna con el contenido de la tarea (título, descripción, contenido)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp) // Espaciado entre checkbox y contenido
            ) {
                // Título de la tarea
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1, // Limitar a 1 línea para que no ocupe demasiado espacio
                    overflow = TextOverflow.Ellipsis // Mostrar "..." si el contenido es muy largo
                )

                // Contenido de la tarea
                Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre descripción y contenido
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2, // Limitar a un número de líneas razonable
                    overflow = TextOverflow.Ellipsis // Mostrar "..." si es muy largo
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    MyNotesTheme {
        TaskItem(
            NoteTask(
                id = 0,
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
        )
    }
}