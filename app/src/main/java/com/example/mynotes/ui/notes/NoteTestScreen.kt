package com.example.mynotes.ui.notes

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.window.Dialog
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
import com.example.mynotes.utils.formatDate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

object NoteTestDestination : NavigationDestination {
    override val route = "note_edit_test"
    override val titleRes = R.string.edit_note
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route?$noteIdArg={$noteIdArg}" // Argumento opcional
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NoteTestScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteTestViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val note: NoteTask = viewModel.noteTask

    var mediaFiles by remember { mutableStateOf<MutableList<MediaFile>>(mutableListOf()) }

    var uri : Uri? = null

    var uris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current


    val recordAudioPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.RECORD_AUDIO)

    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    val readMediaPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)

    var showRationaleDialog by remember { mutableStateOf(false) }
    var enableRationalDialogAudio by remember { mutableStateOf(false) }
    var enableRationalDialogReadExternal by remember { mutableStateOf(false) }
    var enableRationalDialogCamera by remember { mutableStateOf(false) }
    var permissionToRequest by remember { mutableStateOf<PermissionState?>(null) }


    LaunchedEffect(note.id) {
        viewModel.loadMediaFiles(note.id) // Cargar MediaFiles al iniciar
        // Convertir MediaFiles a URIs y actualizar la lista 'uris'
        viewModel.mediaFiles.collect { mediaFiles ->
            if (mediaFiles.isEmpty()) {
                Log.d("NoteTestScreen", "La lista de MediaFiles está vacía para la nota ${note.id}")
            } else {
                Log.d("NoteTestScreen", "MediaFiles para la nota ${note.id}:")
                mediaFiles.forEach { mediaFile ->
                    Log.d("NoteTestScreen", "  - filePath: ${mediaFile.filePath}")
                }
            }
            uris = mediaFiles.map {
                Uri.parse(it.filePath)
            }
        }
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->

            if (uri != null) {
                uris = uris + uri // Agrega la nueva imagen a la lista cuando no es nula la uri, osea si seleccionó una pues
                val mediaFile = MediaFile(
                    noteTaskId = 0,
                    filePath = uri.toString(),
                    mediaType = MediaType.IMAGE
                )
                mediaFiles.add(mediaFile)

                Log.d("TXT", uri.toString())
                Log.d("Path", mediaFile.filePath)

            }

        }

    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->

            if (success) {
                uris = uris + uri!!
                val mediaFile = MediaFile(
                    noteTaskId = 0,
                    filePath = uri!!.toString(),
                    mediaType = MediaType.IMAGE
                )
                mediaFiles.add(mediaFile)


                Log.d("TXT", uri.toString())
                Log.d("Path", mediaFile.filePath)
            }

        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->

            if (success) {
                uris = uris + uri!!
                val mediaFile = MediaFile(
                    noteTaskId = 0,
                    filePath = uri!!.toString(),
                    mediaType = MediaType.VIDEO
                )
                mediaFiles.add(mediaFile)

                Log.d("TXT", uri.toString())
                Log.d("Path", mediaFile.filePath)

            }

        }
    )

    if (showRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = { Text(text = "Permiso requerido") },
            text = { Text(text = "Para acceder a esta función debes otorgar los permisos necesarios.") },
            confirmButton = {
                TextButton(onClick = {
                    permissionToRequest?.launchPermissionRequest()
                    showRationaleDialog = false
                }) {
                    Text("Otorgar permiso")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationaleDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            ActionTopNavBar(
                title = stringResource(NoteTestDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                enableActionButtons = false
            )
        },
        bottomBar = {
            BottomActionBarModal(
                content = "Editado: ${formatDate(note.lastEditedDate)}",
                onDeleteClick = {
                    viewModel.deleteNoteTask()
                    navigateBack()
                },
                onTakePhoto = {

                    if (cameraPermissionState.status.shouldShowRationale && !enableRationalDialogCamera) {
                        cameraPermissionState.launchPermissionRequest()
                        enableRationalDialogCamera = true
                    } else if (cameraPermissionState.status.shouldShowRationale && enableRationalDialogCamera) {
                        showRationaleDialog = true
                        permissionToRequest = cameraPermissionState
                    }else if(cameraPermissionState.status.isGranted){
                        // El permiso ya está otorgado
                        uri = ComposeFileProvider.getImageUri(context)
                        cameraLauncher.launch(uri!!)
                        showRationaleDialog = false
                    }

                },
                onSelectImage = {

                    if (readMediaPermissionState.status.shouldShowRationale && !enableRationalDialogReadExternal) {
                        readMediaPermissionState.launchPermissionRequest()
                        enableRationalDialogReadExternal = true
                    } else if (readMediaPermissionState.status.shouldShowRationale && enableRationalDialogReadExternal) {
                        showRationaleDialog = true
                        permissionToRequest = readMediaPermissionState
                    }else if(readMediaPermissionState.status.isGranted){
                        imagePicker.launch("image/*")
                        showRationaleDialog = false
                    }

                },
                onTakeVideo = {

                    if (cameraPermissionState.status.shouldShowRationale && !enableRationalDialogCamera) {
                        cameraPermissionState.launchPermissionRequest()
                        enableRationalDialogCamera = true
                    } else if (cameraPermissionState.status.shouldShowRationale && enableRationalDialogCamera) {
                        showRationaleDialog = true
                        permissionToRequest = cameraPermissionState
                    }else if(cameraPermissionState.status.isGranted){
                        uri = ComposeFileProvider.getImageUri(context)
                        videoLauncher.launch(uri!!)
                        showRationaleDialog = false
                    }

                },
                onTakeAudio = {

                    if (recordAudioPermissionState.status.shouldShowRationale && !enableRationalDialogAudio) {
                        recordAudioPermissionState.launchPermissionRequest()
                        enableRationalDialogAudio = true
                    } else if (recordAudioPermissionState.status.shouldShowRationale && enableRationalDialogAudio) {
                        showRationaleDialog = true
                        permissionToRequest = recordAudioPermissionState
                    }else if(recordAudioPermissionState.status.isGranted){
                        showRationaleDialog = false
                    }

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
                    value = note.title,
                    onValueChange = {
                        viewModel.updateNoteTask(title = it, description = note.description)
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
                    value = note.description,
                    onValueChange = {
                        viewModel.updateNoteTask(title = note.title, description = it)
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
                /*val imageUris = uris.filter { mediaFile ->
                    mediaFiles.any { it.filePath == mediaFile.toString() && it.mediaType == MediaType.IMAGE }
                }*/
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        items(uris) { uri ->
                            AsyncImage(
                                model = uri,
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(4.dp),
                                contentDescription = "Selected image",
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }

            item {
                val videoUris = uris.filter { mediaFile ->
                    mediaFiles.any { it.filePath == mediaFile.toString() && it.mediaType == MediaType.VIDEO }
                }

                if (videoUris.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        items(videoUris) { videoUri ->
                            // Mostrar la vista previa del video con VideoPlayer
                            VideoPlayer(
                                videoUri = videoUri,
                                modifier = Modifier
                                    .size(150.dp) // Ajusta el tamaño según tu diseño
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }

            /*
            item {
                // TODO: Implementar la misma lógica de las imágenes para los videos, aunque puede cambiar para que sea algo más parecido a un reproductor con barra de progreso
                if (hasVideo && uris.isNotEmpty()) {
                    VideoPlayer(videoUri = uris.last())
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }*/

            item {
                // Botón para guardar
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Ajustes de las fechas de edición y creación
                            if(note.id == 0){
                                note.createdDate = System.currentTimeMillis()
                                note.lastEditedDate = System.currentTimeMillis()
                            }else{
                                note.lastEditedDate = System.currentTimeMillis()
                            }
                            val noteId = viewModel.saveNoteTask()

                            if(noteId > 0){
                                // Guardar los MediaFiles en la base de datos
                                mediaFiles.forEach { mediaFile ->
                                    mediaFile.noteTaskId = noteId // Actualizar el noteTaskId
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

