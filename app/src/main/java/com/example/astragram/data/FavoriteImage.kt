package com.example.astragram.data

data class FavoriteImage(
    //val url: String,                // The original URL of the image
    val localPath: String,          // The local path where the image is saved
    val title: String,              // The title of the image
    val description: String         // The description of the image
)
