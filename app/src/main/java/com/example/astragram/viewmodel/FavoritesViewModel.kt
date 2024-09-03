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

    fun loadFavorites(context: Context) {
        viewModelScope.launch {
            val favoriteImages = loadFavoriteImagesFromLocal(context)
            favoritesLiveData.postValue(favoriteImages)

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
