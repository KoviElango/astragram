package com.example.astragram.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astragram.data.FavoriteImage
import com.example.astragram.utils.loadFavoriteImagesFromLocal
import com.example.astragram.utils.removeFavorite
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    val favoritesLiveData = MutableLiveData<List<FavoriteImage>>() // Holds the list of favorite images

    // Function to load favorite images from local storage
    fun loadFavorites(context: Context) {
        viewModelScope.launch {
            // Fetch images from local storage and ensure the return type matches
            val favoriteImages = loadFavoriteImagesFromLocal(context).mapNotNull { imageName ->
                // Ensure imageName is treated as a String
                val imageNameString = imageName as? String ?: return@mapNotNull null

                // Construct the full path for each image
                val fullPath = "${context.filesDir}/$imageNameString"

                // Safely extract the title without extension
                val title = if (imageNameString.contains(".")) {
                    imageNameString.substringBeforeLast(".")
                } else {
                    imageNameString // In case there is no extension, use the full name
                }

                // Return a new FavoriteImage object
                FavoriteImage(
                    url = "",  // Set to empty or handle accordingly if the original URL is not available
                    localPath = fullPath,
                    title = title, // Use the image name (without extension) as the title
                    description = "Description for $imageNameString"  // Provide a default or dynamic description
                )
            }

            favoritesLiveData.postValue(favoriteImages)

            // Logging the loaded images for debugging
            Log.d("FavoritesViewModel", "Loaded ${favoriteImages.size} favorite images.")
            favoriteImages.forEach { favoriteImage ->
                Log.d("FavoritesViewModel", "Favorite image path: ${favoriteImage.localPath}")
            }
        }
    }

    // Function to remove a favorite image
    fun removeFromFavorites(context: Context, favoriteImage: FavoriteImage) {
        viewModelScope.launch {
            removeFavorite(context, favoriteImage.localPath) // Ensure correct path is used
            loadFavorites(context) // Reload favorites after removal
        }
    }
}
