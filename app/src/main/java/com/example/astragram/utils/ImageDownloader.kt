package com.example.astragram.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.astragram.data.DisplayData
import com.example.astragram.data.FavoriteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL


//this is used to start the download of the image from the provided DisplayData object and adds it to the user's favorites once downloaded successfully.
suspend fun initiateImageDownload(context: Context, displayData: DisplayData) {val localPath = downloadImageAsync(context, displayData.url, displayData.title)

    if (localPath != null) {
        val favoriteImage = FavoriteImage(
            localPath = localPath,
            title = displayData.title,
            description = displayData.description
        )
//Provides feedback to the user via a Toast message and logs the process.
        addFavorite(context, favoriteImage)
        Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
        Log.d("ImageDownloader", "Title from API: ${displayData.title}")
        Log.d("ImageDownloader", "Local path: $localPath")
    } else {
        Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show()}
}

//This function handles the actual downloading of the image from the URL and saves it to the local file system.
//Returns localPath or null if an error occurs.

suspend fun downloadImageAsync(context: Context, imageUrl: String, imageTitle: String): String? {
    return withContext(Dispatchers.IO) {
        try {

            // Saves the image to the local file system and returns the file path or null if an error occurs.
            val fileName = "${imageTitle.replace(" ", "_")}.jpg"
            val file = File(context.filesDir, fileName)

            val inputStream = URL(imageUrl).openStream()
            val outputStream = FileOutputStream(file)

            // Handles the actual downloading of the image from the provided URL.
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            //exception handling
            Log.d("ImageDownloader", "Image successfully saved to ${file.absolutePath}")
            return@withContext file.absolutePath
        } catch (e: Exception) {
            Log.e("ImageDownloader", "Failed to download image: ${e.message}")
            null
        }
    }
}

