package com.example.mynotes.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.preferences.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.mynotes.MyNotesApplication
import com.example.mynotes.utils.myNotesApplication

class SettingsViewModel : ViewModel() {

    private val themePreferences = ThemePreferences(
        context = (myNotesApplication() as MyNotesApplication).applicationContext
    )

    private val _isDarkTheme = MutableStateFlow(themePreferences.isDarkTheme()) // Inicializa con el valor de las preferencias
    val isDarkTheme: StateFlow<Boolean> get() = _isDarkTheme

    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            themePreferences.setDarkTheme(newTheme)
            _isDarkTheme.value = newTheme
        }
    }
}