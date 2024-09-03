package com.example.astragram.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astragram.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(mainViewModel: MainViewModel = viewModel()) {
    val imageUrls by mainViewModel.imageUrlsLiveData.observeAsState(emptyList())
    val errorMessage by mainViewModel.errorMessage.observeAsState()
    val favoriteStateMap = remember { mutableStateMapOf<String, Boolean>() }

    var selectedScreen by remember { mutableStateOf("Home") }

    LaunchedEffect(Unit) {
        mainViewModel.fetchImages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NASA Images") },
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                content = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            selected = selectedScreen == "Home",
                            onClick = { selectedScreen = "Home" }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favourites") },
                            selected = selectedScreen == "Favourites",
                            onClick = { selectedScreen = "Favourites" }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                if (errorMessage != null) {
                    Text(text = errorMessage!!)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(imageUrls) { imageUrl ->
                            val isFavourite = favoriteStateMap[imageUrl] ?: false

                            ImageCard(
                                imageUrl = imageUrl,
                                isFavourite = isFavourite,
                                onFavoriteClick = {
                                    favoriteStateMap[imageUrl] = !isFavourite
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}