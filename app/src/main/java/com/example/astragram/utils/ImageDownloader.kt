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

suspend fun initiateImageDownload(context: Context, displayData: DisplayData) {val localPath = downloadImageAsync(context, displayData.url, displayData.title)

    if (localPath != null) {
        val favoriteImage = FavoriteImage(
            localPath = localPath,
            title = displayData.title,
            description = displayData.description
        )

        addFavorite(context, favoriteImage)
        Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show()}
}

private val imageIdCounter = AtomicInteger(1)

suspend fun downloadImageAsync(context: Context, imageUrl: String, imageTitle: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            val uniqueId = imageIdCounter.getAndIncrement()
            val fileName = "$uniqueId-${imageTitle.replace(" ", "_")}.jpg"
            val file = File(context.filesDir, fileName)

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

