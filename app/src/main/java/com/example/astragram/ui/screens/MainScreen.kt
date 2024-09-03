package com.example.astragram.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val imageUrls by mainViewModel.imageUrlsLiveData.observeAsState(emptyList()) // Observes the LiveData for image URLs
    val errorMessage by mainViewModel.errorMessage.observeAsState()

    // Fetch images when the composable is first composed
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
                // Display the image URLs in a lazy column
                LazyColumn {
                    items(imageUrls) { imageUrl ->
                        Text(text = imageUrl) // Print each image URL
                    }
                }
            }
        }
    }
}