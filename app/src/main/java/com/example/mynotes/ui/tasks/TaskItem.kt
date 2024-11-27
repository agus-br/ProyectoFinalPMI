package com.example.mynotes.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.data.NoteTask
import com.example.mynotes.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun TaskItem(
    task: NoteTask,
    viewModel: TaskItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()

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
                    if (checked) {
                        checked = false
                        task.isCompleted = false
                        coroutineScope.launch {
                            viewModel.updateTask(task)
                        }
                    }
                    else {
                        checked = true
                        task.isCompleted = true
                        coroutineScope.launch {
                            viewModel.updateTask(task)
                        }
                    }
                }
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
