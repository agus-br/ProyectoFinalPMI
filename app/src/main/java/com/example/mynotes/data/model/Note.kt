package com.example.mynotes.data.model

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val content: String,  // Puedes agregar más atributos como archivos, imágenes y audios, los cuales son strings porque se almacenan las rutas
)
