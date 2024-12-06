package com.example.mynotes.ui.multimedia

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import com.example.mynotes.data.MediaFile
import com.example.mynotes.data.MediaType
import com.google.android.exoplayer2.MediaItem

@Composable
fun MediaItemDisplay(
    context: Context,
    mediaFileItem: MediaFile
) {
    when (mediaFileItem.mediaType) {
        MediaType.IMAGE -> DisplayImage(Uri.parse(mediaFileItem.filePath))
        MediaType.VIDEO -> ExoPlayerVideo(Uri.parse(mediaFileItem.filePath))
        MediaType.AUDIO -> AudioPlayer(Uri.parse(mediaFileItem.filePath))
    }
}

