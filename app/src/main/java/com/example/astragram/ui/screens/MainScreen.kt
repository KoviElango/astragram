package com.example.astragram.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.astragram.viewmodel.MainViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.astragram.R


@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val imageUrls by mainViewModel.imageUrlsLiveData.observeAsState(emptyList()) // Observes the LiveData for image URLs
    val errorMessage by mainViewModel.errorMessage.observeAsState()

     LaunchedEffect(Unit) {
        mainViewModel.fetchImages()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            if (errorMessage != null) {
                Text(text = errorMessage!!)
            } else {
                LazyColumn {
                    items(imageUrls) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Images",
                        )
                        Text(text = imageUrl)
                        println(imageUrl)
                    }
                }
            }
        }
    }
}