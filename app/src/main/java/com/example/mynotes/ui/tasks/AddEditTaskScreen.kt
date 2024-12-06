package com.example.mynotes.ui.tasks

import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.mynotes.data.Reminder
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.components.ActionTopNavBar
import com.example.mynotes.ui.components.BottomActionBarModal
import com.example.mynotes.ui.multimedia.ExoPlayerVideo
import com.example.mynotes.ui.navigation.NavigationDestination
import com.example.mynotes.ui.reminders.ReminderItem
import com.example.mynotes.ui.reminders.ReminderList
import com.example.mynotes.utils.SimpleFormatDate
import com.example.mynotes.utils.formatDate
import com.example.mynotes.utils.formatTime
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object AddEditTaskDestination : NavigationDestination {
    override val route = "add_edit_task"
    override val titleRes = R.string.edit_task
    const val taskIdArg = "noteId"
    val routeWithArgs = "$route?$taskIdArg={$taskIdArg}" // Argumento opcional
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
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

    var reminders by remember { mutableStateOf<List<Reminder>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.reminders.collect { loadedReminders ->
            reminders = loadedReminders
        }
    }

    var uri : Uri? = null

    var uris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current


    val notificationPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

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
    var enableRationalDialogNotifications by remember { mutableStateOf(false) }
    var permissionToRequest by remember { mutableStateOf<PermissionState?>(null) }


    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Formateadores para mostrar la fecha y hora
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())

    LaunchedEffect(task.id) {
        viewModel.loadMediaFiles(task.id) // Cargar MediaFiles al iniciar
        // Convertir MediaFiles a URIs y actualizar la lista 'uris'
        viewModel.mediaFiles.collect { mediaFiles ->
            if (mediaFiles.isEmpty()) {
                Log.d("NoteTestScreen", "La lista de MediaFiles está vacía para la nota ${task.id}")
            } else {
                Log.d("NoteTestScreen", "MediaFiles para la nota ${task.id}:")
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
                title = stringResource(AddEditTaskDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                enableActionButtons = true,
                onSetReminder = {

                    if (notificationPermissionState.status.shouldShowRationale && !enableRationalDialogNotifications) {
                        notificationPermissionState.launchPermissionRequest()
                        enableRationalDialogAudio = true
                    } else if (notificationPermissionState.status.shouldShowRationale && enableRationalDialogNotifications) {
                        showRationaleDialog = true
                        permissionToRequest = notificationPermissionState
                    }else if(notificationPermissionState.status.isGranted){

                        showTimePicker(context) { time ->
                            selectedTime = time
                        }

                        showDatePicker(context) { date ->
                            selectedDate = date
                        }

                        val dateTime = LocalDateTime.of(selectedDate, selectedTime)

                        val timeInMillis = dateTime
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()

                        val newReminder = Reminder(
                            noteTaskId = task.id,
                            isActive = true,
                            dateInMillis = timeInMillis, // Agregar la fecha al recordatorio
                            timeInMillis = timeInMillis// Agregar la hora al recordatorio
                        )
                        reminders = reminders + newReminder


                        showRationaleDialog = false
                    }

                }
            )
        },
        bottomBar = {
            BottomActionBarModal(
                content = "Última edición: ${formatDate(task.lastEditedDate)}",
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
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
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

            item{
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
                            ExoPlayerVideo(
                                context = context,
                                uri = videoUri
                            )
                        }
                    }
                }
            }

            /*
            // TODO: Implementar la misma lógica de las imágenes para los videos, aunque puede cambiar para que sea algo más parecido a un reproductor con barra de progreso
            if (hasVideo && uris.isNotEmpty()) {
                VideoPlayer(videoUri = uris.last())
                Spacer(modifier = Modifier.height(16.dp))
            }*/


            item {
                if(reminders.isNotEmpty()){
                    DueDateItem(
                        date = selectedDate.format(dateFormatter),
                        time = selectedTime.format(timeFormatter),
                        onDateClick = {
                            showTimePicker(context) { time ->
                                selectedTime = time
                            }
                        },
                        onTimeClick = {
                            showDatePicker(context) { date ->
                                selectedDate = date
                            }
                        }
                    )
                }
            }

            item{
                // Botón para guardar
                Button(
                    onClick = {
                        coroutineScope.launch {

                            // Ajustes de las fechas de edición y creación
                            if(task.id == 0){
                                task.createdDate = System.currentTimeMillis()
                                task.lastEditedDate = System.currentTimeMillis()
                            }else{
                                task.lastEditedDate = System.currentTimeMillis()
                            }

                            val taskId = viewModel.saveNoteTask()

                            if(taskId > 0){
                                // Guardar los MediaFiles en la base de datos
                                mediaFiles.forEach { mediaFile ->
                                    mediaFile.noteTaskId = taskId // Actualizar el noteTaskId
                                    viewModel.addMediaFile(mediaFile.noteTaskId, mediaFile.filePath, mediaFile.mediaType)
                                }

                                // Guardar los recordatorios
                                reminders.forEach { reminder ->
                                    // Actualizar el ID de la tarea en el recordatorio
                                    reminder.noteTaskId = taskId
                                    if(reminder.id == 0){
                                        viewModel.addReminder(reminder)
                                    }else{
                                        viewModel.updateReminder(reminder)
                                    }
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



@Composable
fun DueDateItem(
    date: String,
    time: String? = "00:00", // Hora de vencimiento en milisegundos (opcional)
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit, // Función a ejecutar al hacer clic en la hora
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Alineación a la izquierda
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recordatorio",
                    style = MaterialTheme.typography.bodyLarge
                )

                TextButton(onClick = onDateClick) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                TextButton(onClick = onTimeClick) {
                    Text(
                        text = time ?: "00:00",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }
    }
}


fun showTimePicker(
    context: Context,
    onTimeSet: (LocalTime) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, hourOfDay, minuteOfHour ->
            onTimeSet(LocalTime.of(hourOfDay, minuteOfHour))
        },
        hour,
        minute,
        true
    ).show()
}

fun showDatePicker(context: Context, onDateSet: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    android.app.DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            onDateSet(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        },
        year,
        month,
        day
    ).show()
}