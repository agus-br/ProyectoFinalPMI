package com.example.mynotes.ui.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.ui.theme.MyNotesTheme

import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.mynotes.ComposeFileProvider

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBarTwoWithBottomSheet(
    content: String,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        /*bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden
        )*/
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            // Contenido del Bottom Sheet
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Opciones", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Acción 1 */ }) {
                    Text("Opción 1")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Acción 2 */ }) {
                    Text("Opción 2")
                }
            }
        },
        sheetPeekHeight = 0.dp // Altura inicial colapsada
    ) {
        BottomAppBar(
            modifier = Modifier.height(48.dp)
        ) {
            NavigationBar {
                Spacer(modifier = Modifier.weight(2f))
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                            /*if (!scaffoldState.bottomSheetState.hasExpandedState) {
                                scaffoldState.bottomSheetState.expand()
                            } else {
                                scaffoldState.bottomSheetState.hide()
                            }*/
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                )
            }
        }
    }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomActionBarModal(
    content: String,
    onDeleteClick: () -> Unit,
    onTakePhoto: () -> Unit,
    onSelectImage: () -> Unit,
    onTakeVideo: () -> Unit,
    onTakeAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                /*Text("Opciones", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))*/
                Button(onClick = onTakePhoto) {
                    Text("Tomar una foto")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onSelectImage) {
                    Text("Sleccionar una imágen")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onTakeVideo) {
                    Text("Grabar un video")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onTakeAudio) {
                    Text("Grabar un audio")
                }
            }
        }
    }

    BottomAppBar{
        NavigationBar {
            NavigationBarItem(
                selected = false,
                onClick = onDeleteClick,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            NavigationBarItem(
                selected = false,
                onClick = { showBottomSheet = true },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Más opciones"
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegratedScreen(
    modifier: Modifier = Modifier
) {

    var uri : Uri? = null

    var uris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasImage by remember { mutableStateOf(false) }
    var hasVideo by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                uris = uris + uri // Agrega la nueva imagen a la lista
            }
            Log.d("TXT", uri.toString())
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            Log.d("IMG", hasImage.toString())
            Log.d("URI", imageUri.toString())
            if(success) imageUri = uri
            uris = uris + uri!!
            hasImage = success

        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            hasVideo = success
        }
    )


    Scaffold(
        bottomBar = {
            BottomActionBarModal(
                content = "Editado ayer",
                onDeleteClick = {},
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
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            // Organizar imágenes en un LazyColumn con filas
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(uris.chunked(2)) { rowUris ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowUris.forEach { uri ->
                            AsyncImage(
                                model = uri,
                                modifier = Modifier
                                    .size(200.dp) // Tamaño uniforme de 200x200dp
                                    .padding(8.dp), // Espaciado entre imágenes
                                contentDescription = "Selected image",
                                contentScale = ContentScale.Fit // Ajusta la imágen
                            )
                        }
                    }
                }
            }

            // Si se seleccionó un video, mostrar el reproductor
            if (hasVideo && uris.isNotEmpty()) {
                VideoPlayer(videoUri = uris.last()) // Muestra el último archivo como video
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SheetDialogTestPreview() {
    MyNotesTheme {
        BottomActionBarModal(
            onDeleteClick = {},
            onSelectImage = {},
            onTakePhoto = {},
            onTakeAudio = {},
            onTakeVideo = {},
            content = "Hello world"
        )
    }
}