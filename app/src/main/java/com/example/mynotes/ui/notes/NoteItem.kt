package com.example.mynotes.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskType
import com.example.mynotes.ui.home.HomeScreen
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun NoteItem(
    note: NoteTask,
    modifier: Modifier = Modifier,
) {
    // Contenedor que ajusta el tamaño vertical según el contenido
    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupar todo el ancho
            .wrapContentHeight(), // Espaciado externo
            //.heightIn(min = 10.dp, max = 250.dp), // Altura mínima y máxima
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp) // Espaciado interno
        ) {
            // Título de la nota
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1, // Limitar a 1 línea para que no ocupe demasiado espacio
                overflow = TextOverflow.Ellipsis // Mostrar "..." si el contenido es muy largo
            )

            // Contenido de la nota
            Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre descripción y contenido
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6, // Limitar a un número de líneas razonable
                overflow = TextOverflow.Ellipsis // Mostrar "..." si es muy largo
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    MyNotesTheme {
        NoteItem(
            NoteTask(
                id = 0,
                title = "Título de un nota",
                description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                        "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                        "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                        "mi vida.",
                type = NoteTaskType.NOTE
            )
        )
    }
}