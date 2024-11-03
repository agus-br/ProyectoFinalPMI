package com.example.mynotes.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.MyNotesTopAppBar
import com.example.mynotes.R
import com.example.mynotes.ui.theme.MyNotesTheme

object SettingDestination {
    const val route = "settings"
    val titleRes = R.string.title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    themeViewModel: SettingsViewModel = viewModel() // Obtén el ViewModel
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

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
            Column(modifier = modifier.padding(padding).padding(16.dp)) {
                Text(
                    text = "Select Theme",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Ícono para el tema claro
                    Icon(
                        imageVector = Icons.Filled.ModeLight,
                        contentDescription = "Light Mode",
                        modifier = Modifier.size(24.dp)
                    )

                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            themeViewModel.isDarkTheme = it
                        }
                    )

                    // Ícono para el tema oscuro
                    Icon(
                        imageVector = Icons.Filled.ModeNight,
                        contentDescription = "Dark Mode",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    MyNotesTheme {
        SettingsScreen(
            navigateBack = {},
            onNavigateUp = {},
            canNavigateBack = false,
        )
    }
}
