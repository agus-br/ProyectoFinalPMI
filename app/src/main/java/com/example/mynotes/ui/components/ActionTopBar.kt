package com.example.mynotes.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.R
import com.example.mynotes.ui.theme.MyNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionTopNavBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    enableActionButtons: Boolean,
    onSetReminder: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = {
            Text(
                title
            ) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (enableActionButtons){
                IconButton(
                    onClick = onSetReminder
                ) {
                    Icon(
                        Icons.Filled.AddAlarm,
                        contentDescription = "Add Note"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ActionTopNavBarPreview() {

    MyNotesTheme {
        ActionTopNavBar(
            title = "Preview",
            canNavigateBack = true,
            navigateUp = {},
            enableActionButtons = true,
            onSetReminder = {}
        )
    }
}