package com.example.mynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.ui.settings.SettingsViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import java.io.File


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

            val settingsViewModel: SettingsViewModel = viewModel()
            var isDarkTheme by remember { mutableStateOf(settingsViewModel.isDarkTheme.value) }

            // Observa los cambios en settingsViewModel.isDarkTheme
            LaunchedEffect(settingsViewModel) {
                launch {
                    settingsViewModel.isDarkTheme.collect { newTheme ->
                        isDarkTheme = newTheme
                    }
                }
            }

            InitApp(
                darkTheme = isDarkTheme,
                activity = this
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun InitApp(
    darkTheme: Boolean,
    activity: MainActivity
){
    MyNotesTheme(darkTheme = darkTheme) {
        val windowSize = calculateWindowSizeClass(activity)
        MyNotesApp(windowSize = windowSize.widthSizeClass)
    }
}
