package com.example.mynotes.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mynotes.ui.navigation.NavigationDestination
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider

// Add/Edit Note Destination
object AddEditTaskDestination : NavigationDestination {
    //const val taskIdArg = "taskId"

    // Implementación de las propiedades requeridas por la interfaz
    //override val route = "addEditTask/{$taskIdArg}"

    // Puedes definir un valor de recurso de cadena para el título
    //override val titleRes = R.string.edit_note // Asegúrate de tener este recurso definido

    // Método para construir la ruta con un ID específico
    //fun route(taskId: Int) = "addEditTask/$taskId"
    override val titleRes = R.string.edit_task
    const val taskIdArg = "taskId"
    override val route = "addEditTask/{$taskIdArg}"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditScreen(
    taskId: Int? = null,
    viewModel: TaskAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory), // Inyección del ViewModel con Factory
    onNavigateBack: () -> Unit // Callback para regresar a la pantalla anterior
) {
    // Cargar la tarea si existe un ID
    LaunchedEffect(taskId) {
        taskId?.let { viewModel.loadTask(it) }
    }

    // Obtener los estados del ViewModel
    val title = viewModel.title.collectAsState()
    val description = viewModel.description.collectAsState()

    // Interfaz de usuario
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (taskId == null) "Add Task" else "Edit Task") },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteTask()
                        onNavigateBack() // Regresar después de eliminar
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Campo para el título de la tarea
                TextField(
                    value = title.value,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo para la descripción de la tarea
                TextField(
                    value = description.value,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )

                // Botón para guardar la tarea
                Button(
                    onClick = {
                        viewModel.saveTask() // Asumiendo que tienes un método para guardar la tarea
                        onNavigateBack() // Regresar después de guardar
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }
            }
        }
    )
}