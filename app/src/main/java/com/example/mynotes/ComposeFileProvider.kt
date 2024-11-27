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
            // 1
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            // 2
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            // 3
            val authority = context.packageName + ".fileprovider"
            // 4
            return getUriForFile(
                context,
                authority,
                file,
            )
        }

        fun getImageUris(context: Context, numImages: Int): List<Uri> {
            val uris = mutableListOf<Uri>()
            repeat(numImages) {
                uris.add(getImageUri(context))
            }
            return uris
        }
    }
}