package com.example.mynotes.ui.multimedia

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mynotes.R

@Composable
fun DisplayImage(
    uri: Uri
) {
    AsyncImage(
        model = uri,
        contentDescription = null,
        contentScale = ContentScale.Crop, // o ContentScale.Inside
        modifier = Modifier
            .fillMaxWidth() // o Modifier.aspectRatio()
            .padding(8.dp),
    )
}