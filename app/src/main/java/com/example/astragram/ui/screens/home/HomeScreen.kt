package com.example.astragram.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.astragram.data.DisplayData
import com.example.astragram.utils.checkAndDownloadImage
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    images: List<DisplayData>,
    errorMessage: String?,
    favoriteStateMap: MutableMap<String, Boolean>
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                    onClick = {
                        coroutineScope.launch {
                            checkAndDownloadImage(context, displayData)
                            favoriteStateMap[displayData.url] = true
                        }
                    }
                )
            }
        }
    }
}
