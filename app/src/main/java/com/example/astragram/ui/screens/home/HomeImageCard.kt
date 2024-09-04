package com.example.astragram.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.astragram.data.DisplayData
import com.example.astragram.ui.screens.ContentDialog
import com.example.astragram.ui.screens.ExpandableText

@Composable
fun ImageCard(
    displayData: DisplayData,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    // State to manage the visibility of the dialog
    val showDetail = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ImageItem(displayData)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ){
                FavouriteButton(
                    isFavourite = isFavourite,
                    onClick = onFavouriteClick
                )
                ImageDetailButton(
                    showDetail = showDetail.value,
                    onClick = onDetailClick
                )
            }

        }
    }

    if (showDetail.value) {
        ContentDialog(displayData, onDismiss = { showDetail.value = false })
    }
}


@Composable
fun ImageDetailButton(
    showDetail: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (showDetail) Icons.Default.Rocket else Icons.Default.Rocket,
            contentDescription = if (showDetail) "Hide Details" else "Show Details",
            tint = if (showDetail) Color.Blue else Color.Gray,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ImageItem(displayData: DisplayData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = displayData.url,
            contentDescription = displayData.title ?: "NASA Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = displayData.title ?: "No Title",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        ExpandableText(displayData.description ?: "No Description")
    }
}

@Composable
fun FavouriteButton(
    isFavourite: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favourite",
            tint = if (isFavourite) Color.Red else Color.Gray,
            modifier = Modifier.size(32.dp)
        )
    }
}
