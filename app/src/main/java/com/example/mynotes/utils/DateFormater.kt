package com.example.mynotes.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Función para formatear solo la fecha
fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es"))
    return format.format(date)
}

// Función para formatear fecha y hora
fun formatDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm", Locale("es"))
    return format.format(date)
}

// Función para formatear la fecha
fun SimpleFormatDate(dateInMillis: Long): String {
    val date = Date(dateInMillis)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

// Función para formatear la hora
fun formatTime(timeInMillis: Long): String {
    val date = Date(timeInMillis)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}