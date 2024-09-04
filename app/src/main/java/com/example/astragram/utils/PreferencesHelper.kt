package com.example.astragram.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.astragram.data.FavoriteImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

private const val PREFS_NAME = "favorites_prefs"
private const val KEY_FAVORITES = "favorites_key"

fun addFavorite(context: Context, favoriteImage: FavoriteImage) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val currentList = getFavorites(context).toMutableList()
    currentList.add(favoriteImage)

    val gson = Gson()
    val jsonString = gson.toJson(currentList)
    editor.putString(KEY_FAVORITES, jsonString)
    editor.apply()
}


fun removeFavorite(context: Context, imagePath: String) {
    val file = File(imagePath)
    if (file.exists()) {
        file.delete()
    }
}

fun getFavorites(context: Context): List<FavoriteImage> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val jsonString = sharedPreferences.getString(KEY_FAVORITES, null)

    return if (!jsonString.isNullOrEmpty()) {
        val type = object : TypeToken<List<FavoriteImage>>() {}.type
        Gson().fromJson(jsonString, type)
    } else {
        emptyList()
    }
}

fun isImageFavorited(context: Context, imagePath: String): Boolean {
    val favorites = getFavorites(context)
    return favorites.any { it.localPath == imagePath }
}
