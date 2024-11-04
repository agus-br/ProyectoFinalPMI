package com.example.mynotes.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Brightness7
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
    val titleRes = R.string.settings_title
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Ícono para el tema claro
                    Text(
                        text = stringResource(R.string.dark_mode_option),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .wrapContentHeight()
                    )

                    Box(
                        modifier = Modifier
                            .size(
                                width = 80.dp,
                                height = 30.dp
                            )
                    ) { // Ajusta el ancho y alto aquí
                        Switch(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(),
                            checked = isDarkTheme,
                            onCheckedChange = {
                                themeViewModel.toggleTheme() // Alternar tema
                            },
                            thumbContent = {
                                if (isDarkTheme) {
                                    Icon(
                                        imageVector = Icons.Filled.Bedtime,
                                        contentDescription = "Dark Mode",
                                        modifier = Modifier.size(12.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.Brightness7,
                                        contentDescription = "Light Mode",
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        )
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
        SettingsScreen(
            navigateBack = {},
            onNavigateUp = {},
            canNavigateBack = false,
        )
    }
}
