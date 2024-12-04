package com.example.mynotes.ui.reminders

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.Reminder

@Composable
fun ReminderList(
    reminders: List<Reminder>,
    onDateClick: (Reminder) -> Unit,
    onTimeClick: (Reminder) -> Unit,
    onDeleteReminder: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(reminders) { reminder ->
            ReminderItem(
                reminder = reminder,
                onDateClick = { onDateClick(reminder) },
                onTimeClick = { onTimeClick(reminder) },
                onDeleteReminder = { onDeleteReminder(reminder) },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReminderList() {
    val sampleReminders = listOf(
        Reminder(id = 1, noteTaskId = 0, timeInMillis = 54000000, isActive = true), // 15:00 (3 PM)
        Reminder(id = 2, noteTaskId = 0, timeInMillis = 56700000, isActive = false), // 15:45 (3:45 PM)
        Reminder(id = 3, noteTaskId = 0, timeInMillis = 37800000, isActive = true),  // 10:30 (10:30 AM)
        Reminder( // Recordatorio con fecha
            id = 4,
            noteTaskId = 0,
            dateInMillis = 1701283200000L, // 29/11/2024
            timeInMillis = 68400000, // 19:00 (7 PM)
            isActive = true
        ),
        Reminder( // Otro recordatorio con fecha
            id = 5,
            noteTaskId = 0,
            dateInMillis = 1701369600000L, // 30/11/2024
            timeInMillis = 41400000, // 11:30 (11:30 AM)
            isActive = false
        )
    )
    ReminderList(
        reminders = sampleReminders,
        onDateClick = { /* TODO: Implementar */ },
        onTimeClick = { /* TODO: Implementar */ },
        onDeleteReminder = { /* TODO: Implementar */ }
    )
}