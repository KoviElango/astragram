package com.example.astragram.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.astragram.data.DisplayData
import com.example.astragram.ui.screens.ContentDialog
import com.example.astragram.utils.initiateImageDownload
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    images: List<DisplayData>,
    errorMessage: String?,
    favoriteStateMap: MutableMap<String, Boolean>
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedImage by remember { mutableStateOf<DisplayData?>(null) }

    if (errorMessage != null) {
        Text(text = errorMessage)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images) { displayData ->
                val isFavourite = favoriteStateMap[displayData.url] ?: false

                ImageCard(
                    displayData = displayData,
                    isFavourite = isFavourite,
                    onFavouriteClick = {
                        coroutineScope.launch {
                            initiateImageDownload(context, displayData)
                            favoriteStateMap[displayData.url] = true
                        }
                    },
                    onDetailClick = {
                        selectedImage = displayData // Set the selected image to display in the dialog
                    }
                )
            }
        }
    }

    // Show dialog if selectedImage is not null
    selectedImage?.let { displayData ->
        ContentDialog(
            displayData = displayData,
            onDismiss = { selectedImage = null } // Dismiss the dialog when closed
        )
    }
}
