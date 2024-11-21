package com.example.mynotes.preferences

import android.content.Context
import android.content.SharedPreferences

class ThemePreferences(
    context: Context
) {

    companion object {
        private const val PREFS_NAME = "settings_prefs"
        private const val DARK_THEME_KEY = "dark_theme"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Obtener el estado del tema
    fun isDarkTheme(): Boolean {
        return preferences.getBoolean(DARK_THEME_KEY, false)
    }

    // Guardar el estado del tema
    fun setDarkTheme(isDarkTheme: Boolean) {
        preferences.edit()
            .putBoolean(DARK_THEME_KEY, isDarkTheme)
            .apply()
    }
}