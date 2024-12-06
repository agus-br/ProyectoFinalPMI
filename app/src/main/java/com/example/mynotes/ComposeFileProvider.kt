package com.example.mynotes

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
){


    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(context, authority, file)
        }

        fun getVideoUri(context: Context): Uri {
            val directory = File(context.cacheDir, "videos")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_video_",
                ".mp4",
                directory
            )

            val authority = context.packageName + ".fileprovider"
            return getUriForFile(context, authority, file)
        }

        fun getAudioUri(context: Context): Uri {
            val directory = File(context.cacheDir, "audios")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_audio_",
                ".mp3",
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(context, authority, file)
        }
    }
}