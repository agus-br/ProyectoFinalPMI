package com.example.mynotes.ui.home

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mynotes.R
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    @StringRes
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToNewNoteTask: () -> Unit, // Navegar a agregar una nota o tarea
    navigateToUpdateNoteTask: (Int) -> Unit, // Navegar a editar un elemento existente
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

}