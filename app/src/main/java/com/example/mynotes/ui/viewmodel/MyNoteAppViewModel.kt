package com.example.mynotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mynotes.ui.viewmodel.NotesViewModel
import com.example.mynotes.ui.viewmodel.TasksViewModel
import com.example.mynotes.data.model.Note
import com.example.mynotes.data.model.Task

class MyNotesAppViewModel(
    private val notesViewModel: NotesViewModel = NotesViewModel(),
    private val tasksViewModel: TasksViewModel = TasksViewModel()
) : ViewModel() {

    // Funciones relacionadas con notas
    fun addNote(note: Note) {
        notesViewModel.addNote(note)
    }

    fun editNote(updatedNote: Note) {
        notesViewModel.editNote(updatedNote)
    }

    fun deleteNote(noteId: String) {
        notesViewModel.deleteNote(noteId)
    }

    fun getNotes() = notesViewModel.notes

    // Funciones relacionadas con tareas
    fun addTask(task: Task) {
        tasksViewModel.addTask(task)
    }

    fun editTask(updatedTask: Task) {
        tasksViewModel.editTask(updatedTask)
    }

    fun deleteTask(taskId: String) {
        tasksViewModel.deleteTask(taskId)
    }

    fun getTasks() = tasksViewModel.tasks
}
