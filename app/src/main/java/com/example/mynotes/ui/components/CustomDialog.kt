package com.example.mynotes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Encabezado") },
        text = {
            Text("Texto de ejemplo")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Texto")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PreviewCustomDialog(){
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {}
    )
}
