package com.example.mynotes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.ui.navigation.MyNotesNavHost

/**
 * Punto de entrada de la aplicación de notas, que configura la navegación entre pantallas.
 */
@Composable
fun MyNotesApp(navController: NavHostController = rememberNavController()) {
    MyNotesNavHost(navController = navController)
}
