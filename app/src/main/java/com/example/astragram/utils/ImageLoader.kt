package com.example.astragram.utils

import android.content.Context
import com.example.astragram.data.FavoriteImage

fun loadFavoriteImagesFromLocal(context: Context): List<FavoriteImage> {
    val filesDir = context.filesDir
    val imageFiles = filesDir.listFiles { file ->
        file.extension == "jpg" // Filter by jpg extension
    } ?: return emptyList()

    return imageFiles.map { file ->
        val id = file.nameWithoutExtension // Use the file name (ID) as the image identifier
        FavoriteImage(
            url = "", // Original URL is not available after download; handle accordingly
            localPath = file.absolutePath,
            title = "Image $id", // Display a simple title using the ID
            description = "Description for Image $id"
        )
    }
}

