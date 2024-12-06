package com.example.mynotes.ui.notes

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mynotes.AndroidAudioRecorder
import com.example.mynotes.ComposeFileProvider
import com.example.mynotes.R
import com.example.mynotes.ui.components.VideoPlayer
import com.example.mynotes.data.MediaFile
import com.example.mynotes.data.MediaType
import com.example.mynotes.data.NoteTask
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.components.ActionTopNavBar
import com.example.mynotes.ui.components.BottomActionBarModal
import com.example.mynotes.ui.multimedia.MediaItemDisplay
import com.example.mynotes.ui.navigation.NavigationDestination
import com.example.mynotes.utils.formatDate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import java.io.File

object NoteTestDestination : NavigationDestination {
    override val route = "note_edit_test"
    override val titleRes = R.string.edit_note
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route?$noteIdArg={$noteIdArg}" // Argumento opcional
}


@RequiresApi(Build.VERSION_CODES.Q)
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

    val mediaFiles by viewModel.mediaFiles.collectAsState(initial = emptyList())

    var uri : Uri? = null

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


    /*val recorder by lazy {
        AndroidAudioRecorder(context)
    }*/

    //var audioFile: File? = null
    var isRecording by remember { mutableStateOf(false) }
    var mediaRecorder: MediaRecorder? = null
    var audioFile: File? by remember { mutableStateOf(null) }

    fun startRecording(
        context: Context
    ) {


        //audioFile = File(uri!!.path!!)

        mediaRecorder = MediaRecorder().apply {
            try {
                audioFile = File(context.cacheDir, "audios/miaudio.m4a")
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile?.absolutePath)
                prepare()
                start()
                isRecording = true
            } catch (e: Exception) {
                Log.e("Recording Error", "Error initializing MediaRecorder", e)
                mediaRecorder?.release() // Liberar recursos en caso de error
                mediaRecorder = null
                isRecording = false
            }
        }
    }


    fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            Log.e("Recording Error", "Error stopping and releasing MediaRecorder", e)
        } finally {
            mediaRecorder = null
            isRecording = false
        }

        // Agregar el archivo de audio grabado a la lista de seleccionados
        audioFile?.let { file ->
            uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
            try {
                if (context.contentResolver.getType(uri!!)?.startsWith("video/") == true) {
                    val correctedUri = Uri.fromFile(file)
                    viewModel.addMediaFile(
                        filePath = correctedUri.toString(),
                        mediaType = MediaType.AUDIO
                    )
                    Log.d("Last audio recorder", correctedUri.toString())
                } else {
                    viewModel.addMediaFile(
                        filePath = uri.toString(),
                        mediaType = MediaType.AUDIO
                    )
                    Log.d("Last audio recorder", uri.toString())
                }
            } catch (e: Exception) {
                Log.e("URI Error", "Error processing the URI for audio file", e)
            }
        }
    }



    fun selectImageFromGallery(
        context: Context,
        launcher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { contentUri ->

                val mimeType = context.contentResolver.getType(contentUri)

                val details = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "image_" + System.currentTimeMillis())
                    put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val imageUri = context.contentResolver.insert(collection, details)

                imageUri?.let {
                    context.contentResolver.openOutputStream(it)?.use { outputStream ->
                        context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }

                    details.clear()
                    details.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(it, details, null, null)

                    viewModel.addMediaFile(
                        filePath = it.toString(),
                        mediaType = MediaType.IMAGE
                    )
                    Log.d("Last image Selected", it.toString())
                }
            }
        }
    }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->

            if (success) {
                viewModel.addMediaFile(
                    filePath = uri.toString(),
                    mediaType = MediaType.IMAGE
                )
                Log.d("Last camera captured", uri.toString())
            }

        }
    )


    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if (success) {
                viewModel.addMediaFile(
                    filePath = uri.toString(),
                    mediaType = MediaType.VIDEO
                )
                Log.d("Last video captured", uri.toString())
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
                        //
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
                        //imagePicker.launch("image/*")
                        selectImageFromGallery(context, galleryLauncher)
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
                        uri = ComposeFileProvider.getVideoUri(context)
                        videoLauncher.launch(uri!!)
                        // Llamar al launcher
                        //videoLauncher.launch(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
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
                        uri = ComposeFileProvider.getAudioUri(context)
                        if (!isRecording) {
                            startRecording(context)
                            /*File(uri!!.toString()).also {
                                recorder.start(it)
                                audioFile = it
                                isRecording = true
                            }*/
                        } else {
                            //recorder.stop()
                            stopRecording()
                            isRecording = false
                        }
                        showRationaleDialog = false
                    }

                }
            )
        },
        floatingActionButton = {
            if (isRecording) {
                FloatingActionButton(
                    onClick = {
                        stopRecording()
                        isRecording = false
                        /*viewModel.addMediaFile(
                            filePath = uri.toString(),
                            mediaType = MediaType.AUDIO
                        )*/
                        //Log.d("Last audio recorder", uri.toString())
                    },
                    shape = CircleShape,
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                ) {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = "Detener grabación",
                        modifier = Modifier.size(32.dp) // Aumentar el tamaño del icono
                    )
                }
            }
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

            items(mediaFiles) { mediaFile ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    MediaItemDisplay(
                        context = context,
                        mediaFileItem = mediaFile
                    )

                    IconButton(
                        onClick = {
                            // Mostrar un AlertDialog para confirmar la eliminación
                            // ...
                            viewModel.removeMediaFile(mediaFile)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd) // Colocar en la esquina superior derecha
                            .padding(4.dp) // Ajustar el padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete, // Usar un icono de "basura"
                            contentDescription = "Eliminar archivo"
                        )
                    }
                }
            }

            item {
                // Botón para guardar
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Ajustes de las fechas de edición y creación
                            if (note.id == 0) {
                                note.createdDate = System.currentTimeMillis()
                                note.lastEditedDate = System.currentTimeMillis()
                            } else {
                                note.lastEditedDate = System.currentTimeMillis()
                            }

                            val noteId = viewModel.saveNoteTask() // Guarda la nota y los archivos multimedia

                            if (noteId > 0) {
                                navigateBack() // Navega de regreso si la operación fue exitosa
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

