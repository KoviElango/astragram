package com.example.astragram.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageCard(
    imageUrl: String,
    isFavourite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ImageItem(imageUrl)
        FavouriteButton(
            isFavourite = isFavourite,
            onClick = onFavoriteClick
        )
    }
}

@Composable
fun ImageItem(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "NASA Image",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
    Text(
        text = "imageName",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 8.dp)
    )
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

@Preview(showBackground = true)
@Composable
fun PreviewImageCard() {
        ImageCard(
            imageUrl = "https://images-assets.nasa.gov/image/PIA15658/PIA15658~thumb.jpg",
            isFavourite = false,
            onFavoriteClick = {}
        )
    }

