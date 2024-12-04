package com.example.mynotes.ui.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mynotes.MyNotesTopAppBar
import com.example.mynotes.ui.theme.MyNotesTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerTest(

){
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(initialHour = 0, initialMinute = 0, is24Hour = true)

    MyNotesTheme {
        Scaffold(
            topBar = {
                MyNotesTopAppBar(
                    title = "hello",
                    canNavigateBack = false,
                    onClickActionSettings = { },
                )
            }
        ){ innerPadding ->
                Column (
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 100.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    OutlinedButton( onClick = { showDatePickerDialog = true } ) { Text("Fecha") }
                    OutlinedButton( onClick = { showTimePickerDialog = true } ) { Text("Hora") }

                    if(showDatePickerDialog){
                        DatePickerDialog(
                            onDismissRequest = { showDatePickerDialog = false },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showDatePickerDialog = false
                                    }
                                ) {
                                    Text(text = "OK")
                                }
                            },
                            dismissButton = {
                                OutlinedButton(
                                    onClick = {
                                        showDatePickerDialog = false
                                    }
                                ) {
                                    Text(text = "Cerrar")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    if (showTimePickerDialog) {
                        Dialog(
                            onDismissRequest = { showTimePickerDialog = false }
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                TimePicker(
                                    state = timePickerState
                                )
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ){
                                    Button( onClick = { showTimePickerDialog = false } ) { Text("Aceptar") }
                                }
                            }

                        }
                    }

                    val date = datePickerState.selectedDateMillis
                    date?.let {
                        val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                        Text("Fecha seleccionada: ${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}")

                    }

                    Text("Hora seleccionada: ${timePickerState.hour}:${timePickerState.minute}")

            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    DatePickerTest()
}