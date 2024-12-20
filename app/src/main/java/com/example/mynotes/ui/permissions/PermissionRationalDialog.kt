package com.example.mynotes.ui.permissions

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun PermissionRationaleDialog(
    rationaleState: RationaleState
) {
    AlertDialog(
        onDismissRequest = { rationaleState.onRationaleReply(false) },
        title = { Text(text = rationaleState.title) },
        text = { Text(text = rationaleState.rationale) },
        confirmButton = {
            TextButton(
                onClick = { rationaleState.onRationaleReply(true) }
            ) {
                Text("Continue")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { rationaleState.onRationaleReply(false) }
            ) {
                Text("Dismiss")
            }
        }
    )
}

data class RationaleState(
    val title: String,
    val rationale: String,
    val onRationaleReply: (proceed: Boolean) -> Unit,
)