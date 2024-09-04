package com.example.astragram.viewmodel.additionalFeatures

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast

//it's a fun additional feature; to set your phone's wallpaper; we get the phone's display dimentions form displayMetrics library and use it to crop the image to fit the screen.

fun setWallpaper(context: Context, imagePath: String) {
    val wallpaperManager = WallpaperManager.getInstance(context)

    try {
        val bitmap = BitmapFactory.decodeFile(imagePath)

        val displayMetrics = context.resources.displayMetrics
        val desiredWidth = displayMetrics.widthPixels
        val desiredHeight = displayMetrics.heightPixels
        wallpaperManager.suggestDesiredDimensions(desiredWidth, desiredHeight)

        wallpaperManager.setBitmap(bitmap)

        Toast.makeText(context, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
        Log.d("Wallpaper", "Wallpaper set successfully")

    } catch (e: Exception) {
        Toast.makeText(context, "Failed to set wallpaper: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("Wallpaper", "Failed to set wallpaper: ${e.message}")
    }
}
