package com.example.mynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.ui.settings.SettingsViewModel
import com.example.mynotes.ui.theme.MyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitApp()
        }
    }
}

@Composable
fun InitApp(
    themeViewModel: SettingsViewModel = viewModel()
) {
    // Observa el estado del tema en el ViewModel
    val isDarkTheme = themeViewModel.isDarkTheme

    MyNotesTheme(darkTheme = isDarkTheme) {
        MyNotesApp()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        MyNotesApp()
    }
}