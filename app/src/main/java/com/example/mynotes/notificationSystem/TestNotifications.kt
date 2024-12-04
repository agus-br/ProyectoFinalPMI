package com.example.mynotes.notificationSystem

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ReminderInfo
import android.content.Context
import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationTestScreen : ComponentActivity() {
    // ... (resto del código sin cambios) ...
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTestScreenContent() {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Formateadores para mostrar la fecha y hora
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(

                title = { Text("Prueba de Notificación") }

            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") }
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar la hora seleccionada
            Text("Hora seleccionada: ${selectedTime.format(timeFormatter)}")
            Spacer(modifier = Modifier.height(8.dp))
            // Mostrar la fecha seleccionada
            Text("Fecha seleccionada: ${selectedDate.format(dateFormatter)}")

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showTimePicker(context) { time ->
                selectedTime = time
            } }) {
                Text("Seleccionar Hora")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showDatePicker(context) { date ->
                selectedDate = date
            } }) {
                Text("Seleccionar Fecha")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val dateTime = LocalDateTime.of(selectedDate, selectedTime)

                val timeInMillis = dateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

                val reminder = ReminderInfo(
                    id = 1,
                    title = title,
                    description = description,
                    timeInMillis = timeInMillis
                )

                Log.d("Captured DateTime: ", timeInMillis.toString())
                Log.d("Current DateTime: ", System.currentTimeMillis().toString())

                NotificationScheduler.scheduleNotification(context, reminder)
            }) {
                Text("Programar Notificación")
            }
        }
    }
}

fun showTimePicker(
    context: Context,
    onTimeSet: (LocalTime) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, hourOfDay, minuteOfHour ->
            onTimeSet(LocalTime.of(hourOfDay, minuteOfHour))
        },
        hour,
        minute,
        true
    ).show()
}

fun showDatePicker(context: Context, onDateSet: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            onDateSet(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        },
        year,
        month,
        day
    ).show()
}