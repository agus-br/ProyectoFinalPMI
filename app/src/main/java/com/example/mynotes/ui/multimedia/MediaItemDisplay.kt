package com.example.mynotes.ui.multimedia

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import com.example.mynotes.data.MediaFile
import com.example.mynotes.data.MediaType
import com.google.android.exoplayer2.MediaItem

@Composable
fun MediaItemDisplay(context: Context, mediaFileItem: MediaFile) {
    when (mediaFileItem.mediaType) {
        MediaType.IMAGE -> DisplayImage(mediaFileItem.filePath)
        MediaType.VIDEO -> VideoPlayer(Uri.parse(mediaFileItem.filePath), context)
        MediaType.AUDIO -> AudioPlayer(Uri.parse(mediaFileItem.filePath), context)
    }
}

