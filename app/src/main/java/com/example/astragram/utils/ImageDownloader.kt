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
import java.util.concurrent.atomic.AtomicInteger

suspend fun checkAndDownloadImage(context: Context, displayData: DisplayData) {
    val localPath = downloadImageAsync(context, displayData.url)

    if (localPath != null) {
        // Create and store the FavoriteImage with the unique ID path
        val favoriteImage = FavoriteImage(
            url = displayData.url,
            localPath = localPath,
            title = displayData.title,
            description = displayData.description
        )

        // Add to local storage or in-memory list
        addFavorite(context, favoriteImage)
        Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show()
    }
}
private val imageIdCounter = AtomicInteger(1)

suspend fun downloadImageAsync(context: Context, imageUrl: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            val uniqueId = imageIdCounter.getAndIncrement() // Generate a unique ID
            val fileName = "$uniqueId.jpg"
            val file = File(context.filesDir, fileName)

            // Download the image from the URL
            val inputStream = URL(imageUrl).openStream()
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            Log.d("ImageDownloader", "Image successfully saved to ${file.absolutePath}")
            return@withContext file.absolutePath
        } catch (e: Exception) {
            Log.e("ImageDownloader", "Failed to download image: ${e.message}")
            null
        }
    }
}

