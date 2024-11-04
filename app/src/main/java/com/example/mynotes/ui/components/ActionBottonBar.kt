package com.example.mynotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.R
import com.example.mynotes.ui.theme.MyNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBottonBar (
    title: String,
    onActionLeft: () -> Unit = {},
    onActionRight: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
){
    CenterAlignedTopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(
                onClick = onActionRight
            ) {
                Icon(Icons.Filled.AttachFile, contentDescription = "Opciones")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = onActionLeft
            ) {
                Icon(
                    imageVector = Filled.Delete,
                    contentDescription = "otras opciones"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ActionBottonBarPreview() {

    MyNotesTheme {
        ActionBottonBar(
            title = "Preview",
            onActionLeft = {},
            onActionRight = {}
        )
    }
}