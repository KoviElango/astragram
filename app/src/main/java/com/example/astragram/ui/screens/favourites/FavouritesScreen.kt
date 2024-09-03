package com.example.astragram.ui.screens.favourites

import FavoriteImageCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astragram.data.FavoriteImage
import com.example.astragram.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen() {
    val favoritesViewModel: FavoritesViewModel = viewModel()
    val favoriteImages by favoritesViewModel.favoritesLiveData.observeAsState(emptyList())
    val context = LocalContext.current // Retrieve the current Context

    // Load favorites as soon as the screen opens
    LaunchedEffect(Unit) {
        favoritesViewModel.loadFavorites(context)
    }

    LazyColumn {
        items(favoriteImages) { favorite: FavoriteImage ->
            FavoriteImageCard(favorite = favorite, onRemoveClick = {
                favoritesViewModel.removeFromFavorites(context, favorite)
            })
        }
    }
}
