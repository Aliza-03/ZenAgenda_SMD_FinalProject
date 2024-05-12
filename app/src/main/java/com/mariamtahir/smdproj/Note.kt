package com.mariamtahir.smdproj

import android.net.Uri

data class Note(
    val title: String,
    val content: String,
    val imageUri: Uri? // Adding imageUri field
)
