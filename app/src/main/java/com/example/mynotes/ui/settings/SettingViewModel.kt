package com.example.mynotes.ui.settings

import androidx.lifecycle.ViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

class SettingsViewModel : ViewModel() {
    var isDarkTheme by mutableStateOf(false)

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}
