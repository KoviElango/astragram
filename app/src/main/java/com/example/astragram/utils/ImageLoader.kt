package com.example.astragram.utils

import android.content.Context
import com.example.astragram.data.FavoriteImage

fun loadFavoriteImagesFromLocal(context: Context): List<FavoriteImage> {
    val filesDir = context.filesDir
    val imageFiles = filesDir.listFiles { file ->
        file.extension == "jpg"
    } ?: return emptyList()

    return imageFiles.map { file ->
        val id = file.nameWithoutExtension
        FavoriteImage(
            localPath = file.absolutePath,
            title = "Image $id",
            description = "Description forImage $id"
        )
    }
}

