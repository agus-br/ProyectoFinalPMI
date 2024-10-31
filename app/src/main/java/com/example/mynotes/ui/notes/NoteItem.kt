package com.example.mynotes.ui.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,  // Acción al hacer clic en la nota
    onLongClick: () -> Unit // Acción al dejar presionado la nota
) {
    // Contenedor que ajusta el tamaño vertical según el contenido
    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupar todo el ancho
            .padding(8.dp) // Espaciado externo
            .heightIn(min = 100.dp, max = 250.dp) // Altura mínima y máxima
            .combinedClickable(
                onClick = onClick, // Acción para el clic
                onLongClick = onLongClick // Acción para la pulsación larga
            ),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray), // Color de fondo
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Espaciado interno
        ) {
            // Título de la nota
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1, // Limitar a 1 línea para que no ocupe demasiado espacio
                overflow = TextOverflow.Ellipsis // Mostrar "..." si el contenido es muy largo
            )

            // Contenido de la nota
            Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre descripción y contenido
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6, // Limitar a un número de líneas razonable
                overflow = TextOverflow.Ellipsis // Mostrar "..." si es muy largo
            )
        }
    }
}