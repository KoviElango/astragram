package com.example.astragram.ui.screens.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astragram.data.FavoriteImage
import com.example.astragram.viewmodel.FavoritesViewModel
import com.example.astragram.viewmodel.additionalFeatures.setWallpaper


@Composable
fun FavoritesScreen() {
    val favoritesViewModel: FavoritesViewModel = viewModel()
    val favoriteImages by favoritesViewModel.favoritesLiveData.observeAsState(emptyList())
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        favoritesViewModel.loadFavorites(context)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(30.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoriteImages) { favorite: FavoriteImage ->
                FavoriteImageCard(
                    favorite = favorite,
                    onRemoveClick = {
                        favoritesViewModel.removeFromFavorites(context, favorite)
                    },
                    onSetWallpaperClick = {
                        setWallpaper(context, favorite.localPath)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}
