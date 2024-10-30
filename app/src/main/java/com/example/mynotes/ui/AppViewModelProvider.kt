package com.example.mynotes.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynotes.MyNotesApplication
import com.example.mynotes.ui.home.HomeViewModel
import com.example.mynotes.ui.notes.NoteAddEditViewModel
import com.example.mynotes.ui.notes.NoteListViewModel
import com.example.mynotes.ui.tasks.TaskAddEditViewModel
import com.example.mynotes.ui.tasks.TaskListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inicializador para HomeViewModel
        initializer {
            HomeViewModel(
                MyNotesApplication().container.noteTaskRepository
            )
        }
        // Inicializador para AddEditNoteViewModel
        initializer {
            NoteAddEditViewModel(
                this.createSavedStateHandle(),
                MyNotesApplication().container.noteTaskRepository
            )
        }

        // Inicializador para NoteListViewModel
        initializer {
            NoteListViewModel(MyNotesApplication().container.noteTaskRepository)
        }

        // Inicializador para AddEditTaskViewModel
        initializer {
            TaskAddEditViewModel(
                this.createSavedStateHandle(),
                MyNotesApplication().container.noteTaskRepository
            )
        }

        // Inicializador para TaskListViewModel
        initializer {
            TaskListViewModel(MyNotesApplication().container.noteTaskRepository)
        }
    }
}