package com.example.mynotes.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mynotes.R
import com.example.mynotes.data.model.Task
import com.example.mynotes.ui.navigation.NavigationDestination

// Add/Edit Note Destination
object AddEditTaskDestination : NavigationDestination {
    const val taskIdArg = "taskId"

    // Implementación de las propiedades requeridas por la interfaz
    override val route = "addEditTask/{$taskIdArg}"

    // Puedes definir un valor de recurso de cadena para el título
    override val titleRes = R.string.edit_note // Asegúrate de tener este recurso definido

    // Método para construir la ruta con un ID específico
    fun route(taskId: Int) = "addEditTask/$taskId"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditScreen(
    taskId: Int,
    navigateBack: () -> Unit,
    task: Task?,
    modifier: Modifier = Modifier
) {
    // Estado para almacenar el texto de los campos
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var content by remember { mutableStateOf(task?.content ?: "") }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Título de la tarea
        OutlinedTextField(
            value = title,
            textStyle = MaterialTheme.typography.titleLarge,
            onValueChange = { title = it },
            placeholder = {
                Text(stringResource(R.string.title))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ancho completo de la pantalla o contenedor
                .height(2.dp) // Grosor de la línea
                .background(Color.Gray) // Color de la línea
                .padding(horizontal = 8.dp)
        )

        // Descripción de la tarea
        OutlinedTextField(
            value = description,
            textStyle = MaterialTheme.typography.titleSmall,
            onValueChange = { description = it },
            placeholder = {
                Text(stringResource(R.string.description))
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedTextColor = Color.Gray,
                focusedTextColor = Color.Gray,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )

        // Contenido de la tarea
        OutlinedTextField(
            value = content,
            textStyle = MaterialTheme.typography.titleMedium,
            onValueChange = { content = it },
            placeholder = {
                Text(stringResource(R.string.content))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
    }
}
