package com.example.astragram.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.astragram.data.DisplayData
import com.example.astragram.ui.screens.ContentDialog
import com.example.astragram.utils.initiateImageDownload
import kotlinx.coroutines.launch
import com.example.astragram.ui.theme.BackgroundWrapper

//Displays a Lazy list of all the 'HomeImageCard's; This is where the Coroutine states are also managed

@Composable
fun HomeScreen(
    images: List<DisplayData>,
    errorMessage: String?,
    favoriteStateMap: MutableMap<String, Boolean>,

    onLoadMore: () -> Unit,
    isLoading: Boolean,

) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedImage by remember { mutableStateOf<DisplayData?>(null) }
    BackgroundWrapper {
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
                            selectedImage =
                                displayData
                        }
                    )
                }
                /**
                 * Although loading indicator is called and modified here, variables isLoading and onLoadMore are passed from the main screen.
                 * Any modifications to the states and behaviour should be managed at MainScreen.kt
                 */
                item{
                    if (isLoading) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                    LaunchedEffect(Unit) {
                        onLoadMore()
                    }
                }
            }
        }
    }

    selectedImage?.let { displayData ->
        ContentDialog(
            displayData = displayData,
            onDismiss = { selectedImage = null }
        )
    }
}
