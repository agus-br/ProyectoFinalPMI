package com.example.mynotes.ui.tasks

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mynotes.ComposeFileProvider
import com.example.mynotes.R
import com.example.mynotes.ui.components.VideoPlayer
import com.example.mynotes.data.MediaFile
import com.example.mynotes.data.MediaType
import com.example.mynotes.data.NoteTask
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.components.ActionTopNavBar
import com.example.mynotes.ui.components.BottomActionBarModal
import com.example.mynotes.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object AddEditTaskDestination : NavigationDestination {
    override val route = "add_edit_task"
    override val titleRes = R.string.edit_task
    const val taskIdArg = "noteId"
    val routeWithArgs = "$route?$taskIdArg={$taskIdArg}" // Argumento opcional
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditTaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val task: NoteTask = viewModel.task

    var mediaFiles by remember { mutableStateOf<MutableList<MediaFile>>(mutableListOf()) }

    var uri : Uri? = null

    var uris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasImage by remember { mutableStateOf(false) }
    var hasVideo by remember { mutableStateOf(false) }

    val context = LocalContext.current


    LaunchedEffect(task.id) {
        viewModel.loadMediaFiles(task.id) // Cargar MediaFiles al iniciar
        // Convertir MediaFiles a URIs y actualizar la lista 'uris'
        viewModel.mediaFiles.collect { mediaFiles ->
            uris = mediaFiles.map { Uri.parse(it.filePath) }
        }
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            Log.d("TXT", uri.toString())
            hasImage = uri != null
            imageUri = uri

            if (uri != null) {
                uris = uris + uri // Agrega la nueva imagen a la lista cuando no es nula la uri, osea si seleccionó una pues
                val mediaFile = MediaFile(
                    noteTaskId = 0,
                    filePath = imageUri.toString(),
                    mediaType = MediaType.IMAGE
                ) // noteTaskId = 0 temporalmente
                mediaFiles.add(mediaFile)
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            Log.d("IMG", hasImage.toString())
            Log.d("URI", imageUri.toString())
            if (success && uri != null) {
                val mediaFile = MediaFile(
                    noteTaskId = 0,
                    filePath = imageUri.toString(),
                    mediaType = MediaType.IMAGE
                ) // noteTaskId = 0 temporalmente
                mediaFiles.add(mediaFile)
            }
            hasImage = success

        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if (success && uri != null) {
                uris = uris + uri!!
                val mediaFile = MediaFile(
                    noteTaskId = 0,
                    filePath = uri!!.toString(),
                    mediaType = MediaType.IMAGE
                ) // noteTaskId = 0 temporalmente
                mediaFiles.add(mediaFile)
            }
            hasVideo = success
        }
    )


    Scaffold(
        topBar = {
            ActionTopNavBar(
                title = stringResource(AddEditTaskDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                enableActionButtons = true,
                onSetReminder = {}
            )
        },
        bottomBar = {
            BottomActionBarModal(
                content = "Editado ayer",
                onDeleteClick = {
                    viewModel.deleteNoteTask()
                    navigateBack()
                },
                onTakePhoto = {
                    uri = ComposeFileProvider.getImageUri(context)
                    cameraLauncher.launch(uri!!)
                    imageUri = uri
                },
                onSelectImage = { imagePicker.launch("image/*") },
                onTakeVideo = {
                    uri = ComposeFileProvider.getImageUri(context)
                    videoLauncher.launch(uri!!)
                    imageUri = uri
                },
                onTakeAudio = {
                    // TODO: Implementar la funcionalidad de grabación de audio
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Campo de título
                OutlinedTextField(
                    value = task.title,
                    onValueChange = {
                        viewModel.updateNoteTask(title = it, description = task.description)
                    },
                    label = { Text(stringResource(R.string.notes)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Campo de descripción
                OutlinedTextField(
                    value = task.description,
                    onValueChange = {
                        viewModel.updateNoteTask(title = task.title, description = it)
                    },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    maxLines = Int.MAX_VALUE, // quitar el límite de altura
                    textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp) // este es para el espacio de las líneas
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (uris.isNotEmpty()) {
                val chunkSize = if (uris.size <= 3) uris.size else 5 // Calcula el tamaño del chunk
                items(uris.chunked(chunkSize)) { rowUris ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp), // Aplica padding al row, aunque yo ni lo noto
                        horizontalArrangement = Arrangement.SpaceAround // Es un espaciado uniforme, tanto en medio de lso elementos como al inicio y al final
                    ) {
                        rowUris.forEach { uri ->
                            Box(
                                modifier = Modifier
                                    .weight(1f) // Asigna peso igual a cada imagen
                                    .aspectRatio(1f) // Mantiene una relación de aspecto 1:1
                                    .padding(4.dp) // Para que no se vean todas pegadas
                            ) {
                                AsyncImage(
                                    model = uri,
                                    modifier = Modifier.fillMaxSize(), // toma el tamaño del box
                                    contentDescription = "Selected image",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
            }

            // TODO: Implementar la misma lógica de las imágenes para los videos, aunque puede cambiar para que sea algo más parecido a un reproductor con barra de progreso
            if (hasVideo && uris.isNotEmpty()) {
                item {
                    VideoPlayer(videoUri = uris.last())
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                // Botón para guardar
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Guarda la nota papu
                            val taskId = viewModel.saveNoteTask()

                            if(taskId > 0){
                                // Guardar los MediaFiles en la base de datos
                                mediaFiles.forEach { mediaFile ->
                                    mediaFile.noteTaskId = taskId // Actualizar el noteTaskId
                                    viewModel.addMediaFile(mediaFile.noteTaskId, mediaFile.filePath, mediaFile.mediaType)
                                }

                                navigateBack()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}

