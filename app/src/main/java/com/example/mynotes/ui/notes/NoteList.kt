package com.example.mynotes.ui.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.NoteTask
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.R
import com.example.mynotes.data.NoteTaskType
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun NoteList(
    noteList: List<NoteTask>,
    noteItemOnclick: (NoteTask) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier // Usa el modifier pasado como parámetro
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = contentPadding, // Usa contentPadding pasado como parámetro
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = noteList, key = { it.id }) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .wrapContentHeight()
                    .clickable { noteItemOnclick(note) } // Aquí se pasa el note
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview() {
    MyNotesTheme {
        NoteList(
            noteList = listOf(
                NoteTask(
                    id = 0,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                            "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                            "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                            "mi vida. Seré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                            "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                            "todo en paz. ",
                    createdDate = System.currentTimeMillis(),
                    lastEditedDate = System.currentTimeMillis(),
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
                    createdDate = System.currentTimeMillis(),
                    lastEditedDate = System.currentTimeMillis(),
                    type = NoteTaskType.NOTE
                ),
                NoteTask(
                    id = 2,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba miedo",
                    createdDate = System.currentTimeMillis(),
                    lastEditedDate = System.currentTimeMillis(),
                    type = NoteTaskType.NOTE
                ),
                NoteTask(
                    id = 3,
                    title = "Título de un nota",
                    description = "Hola, esta es una nota qeu siempre he querido escribir pero me danba " +
                            "miedo, aunque ya lo perdí y ahora me siento mucho mejor y quiero decirle al " +
                            "mundo que a partir de hoy le dejas de importar a mi corazon y empiezo a vivir" +
                            "mi vida.",
                    createdDate = System.currentTimeMillis(),
                    lastEditedDate = System.currentTimeMillis(),
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
                    createdDate = System.currentTimeMillis(),
                    lastEditedDate = System.currentTimeMillis(),
                    type = NoteTaskType.NOTE
                ),
                NoteTask(
                    id = 5,
                    title = "Título de un nota",
                    description = "HoSeré mejor y comensaré olvidadando todo el dolor que sentí cuando " +
                            "ya no me parecía que estuvieras atenta. Para mí fue algo muy bonito y dejaré " +
                            "todo en paz. ",
                    createdDate = System.currentTimeMillis(),
                    lastEditedDate = System.currentTimeMillis(),
                    type = NoteTaskType.NOTE
                )
            ),
            noteItemOnclick = {},
            contentPadding = PaddingValues()
        )
    }
}

