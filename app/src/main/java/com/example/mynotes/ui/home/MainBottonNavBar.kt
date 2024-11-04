package com.example.mynotes.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun MainBottomNavBar(
    onNotesClick: () -> Unit,
    onTasksClick: () -> Unit,
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onNotesClick,
                    modifier = Modifier.weight(1f) // Espaciado uniforme
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Notas"
                    )
                }
                IconButton(
                    onClick = onTasksClick,
                    modifier = Modifier.weight(1f) // Espaciado uniforme
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Tareas"
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainBottomNavBarPreview() {
    MyNotesTheme {
        MainBottomNavBar(
            onNotesClick = {},
            onTasksClick = {}
        )
    }
}