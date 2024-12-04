package com.example.mynotes.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    val result: String?
    val cursor = context.contentResolver.query(contentUri, null, null, null, null)
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = contentUri.path
    } else {
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}