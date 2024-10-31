package com.example.mynotes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.ui.theme.MyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                MyNotesTheme {
                    MyNotesApp()
                }
            }// Tu lógica de inicialización
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onCreate", e)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        MyNotesApp()
    }
}