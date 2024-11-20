package com.example.mynotes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun PruebaBar(
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

@Composable
fun MainBottomNavBar(
    onNotesClick: () -> Unit,
    onTasksClick: () -> Unit,
) {
    BottomAppBar {
        NavigationBar {
            NavigationBarItem(
                selected = true,
                onClick = onNotesClick,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Notas"
                    )
                },
                label = {
                    Text("Notas")
                }
            )
            NavigationBarItem(
                selected = false,
                onClick = onTasksClick,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Tareas"
                    )
                },
                label = {
                    Text("Tareas")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainBottomNavBarPreview() {
    MyNotesTheme {
        Scaffold (
            bottomBar = {
                MainBottomNavBar(
                    onNotesClick = {},
                    onTasksClick = {}
                )
            }
        ){
            paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ){
                Text("hello")
            }
        }
    }
}