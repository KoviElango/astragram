package com.example.astragram.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astragram.data.FavoriteImage
import com.example.astragram.utils.loadFavoriteImagesFromLocal
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    private val _favoritesLiveData = MutableLiveData<List<FavoriteImage>>()
    val favoritesLiveData: LiveData<List<FavoriteImage>> =
        _favoritesLiveData // Holds the list of favorite images

    init {
        _favoritesLiveData.value = emptyList()
    }

    fun loadFavorites(context: Context) {
        viewModelScope.launch {
            loadFavoriteImagesFromLocal(context)
                .also { favoriteImages ->
                    _favoritesLiveData.postValue(favoriteImages)
                    Log.d("FavoritesViewModel", "Loaded ${favoriteImages.size} favorite images.")
                    favoriteImages.forEach {
                        Log.d("FavoritesViewModel", "Favorite image path: ${it.localPath}")
                    }
                }
        }
    }

    /**
    removeFavorite Function: Deletes a favorite image file from the local storage.
    How it works:
     * Takes the path of the image (imagePath) and creates a File object.
     * Checks if the file exists using file.exists() and deletes it with file.delete() if it does.
     */

    fun removeFromFavorites(favoriteImage: FavoriteImage) {
        val currentFavorites = _favoritesLiveData.value.orEmpty().toMutableList()
        currentFavorites.remove(favoriteImage)
        _favoritesLiveData.value = currentFavorites
    }

    fun setInitialFavoritesForTest(favorites: List<FavoriteImage>) {
        _favoritesLiveData.value = favorites
    }
}
