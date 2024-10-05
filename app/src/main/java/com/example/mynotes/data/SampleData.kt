package com.example.mynotes.data

data class NoteItemData(val title: String, val description: String, val content: String)

val notes = listOf(
    NoteItemData(
        title = "Ideas para nuevo proyecto",
        description = "Brainstorming inicial",
        content = "Reunir ideas y conceptos para un nuevo proyecto de aplicación móvil enfocado en productividad."
    ),
    NoteItemData(
        title = "Receta de pastel de chocolate",
        description = "Ingredientes y pasos",
        content = "Anotar los ingredientes y los pasos detallados para preparar un pastel de chocolate casero."
    ),
    NoteItemData(
        title = "Citas inspiradoras",
        description = "Frases motivacionales",
        content = "Recopilación de frases motivacionales de diferentes autores para el inicio de la semana."
    ),
    NoteItemData(
        title = "Lista de lectura",
        description = "Libros por leer",
        content = "Crear una lista de libros recomendados por amigos para leer en los próximos meses."
    ),
    NoteItemData(
        title = "Notas de la reunión",
        description = "Resumen de la discusión",
        content = "Anotar los puntos clave discutidos durante la reunión del proyecto de desarrollo y las decisiones tomadas."
    )
)

data class TaskItemData(val title: String, val description: String, val content: String, val state: String, val completed: Boolean)

val tasks = listOf(
    TaskItemData(
        title = "Completar reporte mensual",
        description = "Reporte de ventas de septiembre",
        content = "Finalizar el reporte mensual de ventas y enviarlo al gerente antes del viernes.",
        state = "En progreso",
        completed = false
    ),
    TaskItemData(
        title = "Revisión del código",
        description = "Correcciones y mejoras",
        content = "Revisar y corregir el código de la nueva funcionalidad en la aplicación de notas.",
        state = "Pendiente",
        completed = false
    ),
    TaskItemData(
        title = "Planificar reunión",
        description = "Organizar detalles",
        content = "Preparar agenda para la reunión del lunes y coordinar con los participantes.",
        state = "Completado",
        completed = true
    ),
    TaskItemData(
        title = "Actualizar base de datos",
        description = "Migración de datos",
        content = "Realizar la migración de los datos antiguos al nuevo sistema de base de datos.",
        state = "En progreso",
        completed = false
    ),
    TaskItemData(
        title = "Responder correos pendientes",
        description = "Comunicación importante",
        content = "Responder a los correos pendientes relacionados con el proyecto de desarrollo web.",
        state = "Pendiente",
        completed = false
    )
)