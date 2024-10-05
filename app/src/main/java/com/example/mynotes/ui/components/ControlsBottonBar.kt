package com.example.mynotes.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable


@Composable
fun ControlsBottonBar(
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }
        }
    )
}
