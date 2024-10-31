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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    title: String,
    description: String,
    completed: Boolean, // Estado que indica si la tarea está completada
    modifier: Modifier = Modifier,
    onClick: () -> Unit,  // Acción al hacer clic en la tarea
    onLongClick: () -> Unit, // Acción al dejar presionado la tarea
    onCheckedChange: (Boolean) -> Unit // Callback cuando se cambia el estado del checkbox
) {

    var checked by remember { mutableStateOf(completed) }

    // Contenedor que ajusta el tamaño vertical según el contenido
    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupar todo el ancho
            .padding(8.dp) // Espaciado externo
            .heightIn(min = 100.dp, max = 180.dp) // Altura mínima y máxima
            .combinedClickable(
                onClick = onClick, // Acción para el clic
                onLongClick = onLongClick // Acción para la pulsación larga
            ),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray), // Color de fondo
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row( // Usar Row para organizar el checkbox al lado izquierdo de la tarea
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Espaciado interno
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
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1, // Limitar a 1 línea para que no ocupe demasiado espacio
                    overflow = TextOverflow.Ellipsis // Mostrar "..." si el contenido es muy largo
                )

                // Descripción de la tarea
                /*Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre título y descripción
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 1, // Limitar a 2 líneas
                    overflow = TextOverflow.Ellipsis // Mostrar "..." si es muy largo
                )*/

                // Contenido de la tarea
                Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre descripción y contenido
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2, // Limitar a un número de líneas razonable
                    overflow = TextOverflow.Ellipsis // Mostrar "..." si es muy largo
                )
            }
        }
    }
}
