package com.example.mynotes.ui.reminders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.Reminder
import com.example.mynotes.utils.SimpleFormatDate
import com.example.mynotes.utils.formatTime


@Composable
fun ReminderItem(
    reminder: Reminder,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    onDeleteReminder: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isReminderActive by remember { mutableStateOf(reminder.isActive) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDeleteReminder
                ) {
                    Icon(
                        imageVector = Filled.Delete,
                        contentDescription = "Eliminar recordatorio"
                    )
                }

                // Mostrar la fecha solo si está disponible
                if (reminder.dateInMillis != null) {
                    TextButton(
                        onClick = onDateClick,
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text(
                            text = SimpleFormatDate(reminder.dateInMillis!!),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Mostrar la hora
                TextButton(
                    onClick = onTimeClick
                ) {
                    Text(
                        text = formatTime(reminder.timeInMillis),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Switch(
                    checked = isReminderActive,
                    onCheckedChange = { isReminderActive = it }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewReminderItem() {
    ReminderItem(
        reminder = Reminder(
            id = 0,
            noteTaskId = 0,
            timeInMillis = 54000000, // Ejemplo: 15:00 (3 PM)
            isActive = true
        ),
        onDateClick = { /* TODO: Implementar */ },
        onTimeClick = { /* TODO: Implementar */ },
        onDeleteReminder = { /* TODO: Implementar */ }
    )
}