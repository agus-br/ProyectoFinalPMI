package com.example.mynotes.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.MyNotesTopAppBar
import com.example.mynotes.R
import com.example.mynotes.ui.theme.MyNotesTheme

object SettingDestination {
    const val route = "settings"
    val titleRes = R.string.title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
) {
    var expandedTheme by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf("Light") }

    var expandedLanguage by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Spanish") }

    val themes = listOf("Light", "Dark")
    val languages = listOf("English", "Spanish")

    Scaffold(
        topBar = {
            MyNotesTopAppBar(
                title = stringResource(id = SettingDestination.titleRes),
                navigateUp = onNavigateUp,
                canNavigateBack = canNavigateBack,
                onClickActionSettings = {}
            )
        },
        content = { padding ->
            Column(modifier = modifier.padding(padding)) {
                // Dropdown for Theme Selection
                ExposedDropdownMenuBox(
                    expanded = expandedTheme,
                    onExpandedChange = { expandedTheme = !expandedTheme }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedTheme,
                        onValueChange = {},
                        label = { Text("Select Theme") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTheme)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTheme,
                        onDismissRequest = { expandedTheme = false }
                    ) {
                        themes.forEach { theme ->
                            DropdownMenuItem(
                                text = { Text(theme) },
                                onClick = {
                                    selectedTheme = theme
                                    expandedTheme = false
                                    // Aquí puedes agregar la lógica para aplicar el tema
                                }
                            )
                        }
                    }
                }

                // Dropdown for Language Selection
                ExposedDropdownMenuBox(
                    expanded = expandedLanguage,
                    onExpandedChange = { expandedLanguage = !expandedLanguage }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedLanguage,
                        onValueChange = {},
                        label = { Text("Select Language") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLanguage,
                        onDismissRequest = { expandedLanguage = false }
                    ) {
                        languages.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(language) },
                                onClick = {
                                    selectedLanguage = language
                                    expandedLanguage = false
                                    // Aquí puedes agregar la lógica para aplicar el idioma
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    MyNotesTheme {
        SettingScreen(
            navigateBack = {},
            onNavigateUp = {},
            canNavigateBack = false,
        )
    }
}