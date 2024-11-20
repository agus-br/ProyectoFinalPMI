package com.example.mynotes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.MyNotesTopAppBar
import com.example.mynotes.R
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskType
import com.example.mynotes.ui.AppViewModelProvider
import com.example.mynotes.ui.navigation.NavigationDestination
import com.example.mynotes.ui.notes.NoteList
import com.example.mynotes.ui.tasks.TaskList
import com.example.mynotes.ui.theme.MyNotesTheme
import com.example.mynotes.ui.components.SearchBar
import com.example.mynotes.ui.components.MainBottomNavBar

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClickActionSettings: () -> Unit,
    navigateToNewNote: () -> Unit, // Navegar a agregar una nueva nota
    navigateToUpdateNote: (Int) -> Unit, // Navegar a editar una nota existente
    navigateToNewTask: () -> Unit, // Navegar a agregar una nueva tarea
    navigateToUpdateTask: (Int) -> Unit, // Navegar a editar una tarea existente
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var currentListShowing by remember { mutableIntStateOf(1) }
    val homeUiStateNotes by viewModel.homeUiStateNotes.collectAsState()
    val homeUiStateTasks by viewModel.homeUiStateTasks.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column ()
            {
                MyNotesTopAppBar(
                    title = stringResource(HomeDestination.titleRes),
                    canNavigateBack = false,
                    onClickActionSettings = onClickActionSettings,
                    scrollBehavior = scrollBehavior
                )
                SearchBar(
                    placeHolder = "Buscar mis notas",
                    onSearch = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = if (currentListShowing == 1) navigateToNewNote else navigateToNewTask,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_note)
                )
            }
        },
        bottomBar = {
            MainBottomNavBar(
                onNotesClick = {currentListShowing = 1},
                onTasksClick = {currentListShowing = 2}
            )
        }
    ) { innerPadding ->
        HomeBody(
            currentListShowing = currentListShowing,
            itemList = if (currentListShowing == 1) homeUiStateNotes.noteTaskList else homeUiStateTasks.noteTaskList,
            onItemClick = if (currentListShowing == 1) navigateToUpdateNote else navigateToUpdateTask,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    currentListShowing: Int,
    itemList: List<NoteTask>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = "No hay elementos",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp),
            )
        } else {
            if(currentListShowing == 1){
                NoteList(
                    noteList = itemList,
                    noteItemOnclick = { onItemClick(it.id) },
                    contentPadding = contentPadding
                )
            }else{
                TaskList(
                    taskList = itemList,
                    taskItemOnclick = { onItemClick(it.id) },
                    contentPadding = contentPadding
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyTaskListPreview() {

    MyNotesTheme {
        HomeBody(
            currentListShowing = 1,
            itemList = taskList,
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyNoteListPreview() {
    MyNotesTheme {
        HomeBody(
            currentListShowing = 2,
            itemList = noteList,
            onItemClick = {}
        )
    }
}


var noteList = listOf(
    NoteTask(
        id = 0,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.NOTE
    ),
    NoteTask(
        id = 1,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me ",
        type = NoteTaskType.NOTE
    ),
    NoteTask(
        id = 2,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba miedo",
        type = NoteTaskType.NOTE
    ),
    NoteTask(
        id = 3,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida.",
        type = NoteTaskType.NOTE
    ),
    NoteTask(
        id = 4,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo quey comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.NOTE
    ),
    NoteTask(
        id = 5,
        title = "Título de un nota",
        description = "HoSeré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.NOTE
    )
)

var taskList = listOf(
    NoteTask(
        id = 0,
        title = "Título de un nota",
        description = "Hola",
        type = NoteTaskType.TASK,
        isCompleted = false,
        dueDate = 121245
    ),
    NoteTask(
        id = 1,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.TASK,
        isCompleted = false,
        dueDate = 121245
    ),
    NoteTask(
        id = 2,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre.",
        type = NoteTaskType.TASK,
        isCompleted = false,
        dueDate = 121245
    ),
    NoteTask(
        id = 3,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.TASK,
        isCompleted = false,
        dueDate = 121245
    ),
    NoteTask(
        id = 4,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.TASK,
        isCompleted = false,
        dueDate = 121245
    ),
    NoteTask(
        id = 5,
        title = "Título de un nota",
        description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                "todo en paz. ",
        type = NoteTaskType.TASK,
        isCompleted = false,
        dueDate = 121245
    )
)
