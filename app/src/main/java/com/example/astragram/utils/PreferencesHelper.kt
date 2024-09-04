package com.example.astragram.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.astragram.data.FavoriteImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

//The PREFS_NAME and KEY_FAVORITES constants in your code are used to manage data in SharedPreferences

private const val PREFS_NAME = "favorites_prefs"
private const val KEY_FAVORITES = "favorites_key"

/**
 addFavorite Function: Adds a FavoriteImage to the list of favorite images stored in SharedPreferences.
 How it works:
 * Retrieves the current list of favorite images by calling getFavorites.
 * Adds the new favoriteImage to the list.
 * Converts the updated list into a JSON string using Gson.
 * Stores this JSON string in SharedPreferences using editor.putString(KEY_FAVORITES, jsonString) with the KEY_FAVORITES key.
 * editor.apply() is called to save the changes asynchronously.
*/


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


/**
 removeFavorite Function: Deletes a favorite image file from the local storage.
 How it works:
 * Takes the path of the image (imagePath) and creates a File object.
 * Checks if the file exists using file.exists() and deletes it with file.delete() if it does.
*/


fun removeFavorite(context: Context, imagePath: String) {
    val file = File(imagePath)
    if (file.exists()) {
        file.delete()
    }
}

/**
 getFavorites Function: Retrieves the list of favorite images stored in SharedPreferences.
 How it works:
 * Fetches the JSON string associated with KEY_FAVORITES from SharedPreferences.
 * If the JSON string is not empty, it uses Gson to convert the JSON back into a list of FavoriteImage objects.
 * If the JSON string is empty or null, it returns an empty list.
 */

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
