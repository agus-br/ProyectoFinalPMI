    package com.example.mynotes.ui

    import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
    import androidx.lifecycle.createSavedStateHandle
    import androidx.lifecycle.viewmodel.CreationExtras
    import androidx.lifecycle.viewmodel.initializer
    import androidx.lifecycle.viewmodel.viewModelFactory
    import com.example.mynotes.MyNotesApplication
    import com.example.mynotes.ui.home.HomeViewModel
    import com.example.mynotes.ui.notes.NoteTestViewModel
    import com.example.mynotes.ui.tasks.AddEditTaskViewModel
    import com.example.mynotes.ui.tasks.TaskItemViewModel

    object AppViewModelProvider {
        val Factory = viewModelFactory {
            // Inicializador para HomeViewModel
            initializer {
                HomeViewModel(
                    myNotesApplication().container.noteTaskRepository
                )
            }

            // Inicializador para AddEditTaskViewModel
            initializer {
                AddEditTaskViewModel(
                    this.createSavedStateHandle(),
                    myNotesApplication().container.noteTaskRepository
                )
            }

            // Inicializador para AddEditTaskViewModel
            initializer {
                NoteTestViewModel(
                    this.createSavedStateHandle(),
                    myNotesApplication().container.noteTaskRepository
                )
            }

            // Inicializador para TaskItemViewModel
            initializer {
                TaskItemViewModel(
                    this.createSavedStateHandle(),
                    myNotesApplication().container.noteTaskRepository
                )
            }

        }
    }

    /**
     * Extension function to retrieve the instance of [MyNotesApplication] from [CreationExtras].
     */
    fun CreationExtras.myNotesApplication(): MyNotesApplication =
        (this[AndroidViewModelFactory.APPLICATION_KEY] as MyNotesApplication)
