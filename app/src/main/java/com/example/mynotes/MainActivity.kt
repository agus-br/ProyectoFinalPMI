package com.example.mynotes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.ui.settings.SettingsViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import java.io.File
import java.net.URI
import java.time.LocalDateTime


class MainActivity : ComponentActivity() {

    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    private var audioFile: File? = null

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val uri = ComposeFileProvider.getImageUri(applicationContext)
        setContent {
            MyNotesTheme(darkTheme = true) {
                val windowSize = calculateWindowSizeClass(this)
                MyNotesApp(windowSize = windowSize.widthSizeClass)
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        AlarmasScreen(
//                            alarmScheduler = AlarmSchedulerImpl(applicationContext))
                    }
                    ImagePicker()
                    /*GrabarAudioScreen(
                        onClickStGra = {
                            File(cacheDir, "audio.mp3").also {
                                recorder.start(it)
                                audioFile = it
                            }

                        },
                        onClickSpGra = {recorder.stop()},
                        onClickStRe = { audioFile?.let { player.start(it) } },
                        onClickSpRe = {player.stop()}
                    )*/
                }*/
            }
        }
    }
}
/*
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun InitApp(
    themeViewModel: SettingsViewModel = viewModel()
) {
    // Observa el estado del tema en el ViewModel
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    MyNotesTheme(darkTheme = isDarkTheme) {
        //val windowSize = calculateWindowSizeClass(this)
        MyNotesApp()
    }
}*/
/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme(darkTheme = true) {
        MyNotesApp()
    }
}*/