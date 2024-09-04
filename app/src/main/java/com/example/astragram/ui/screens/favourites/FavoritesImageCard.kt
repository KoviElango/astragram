package com.example.astragram.ui.screens.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Wallpaper
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
import com.example.astragram.data.FavoriteImage

@Composable
fun FavoriteImageCard(
    favorite: FavoriteImage,
    onRemoveClick: () -> Unit,
    onSetWallpaperClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Row{
            AsyncImage(
                model = favorite.localPath,
                contentDescription = favorite.title,
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

        }


        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = favorite.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                maxLines = 1
            )

            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Default.HeartBroken,
                    contentDescription = "Remove Favorite",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onSetWallpaperClick) {
                Icon(
                    imageVector = Icons.Default.Wallpaper,
                    contentDescription = "Set as Wallpaper",
                    tint = Color.Black
                )
            }
        }
    }
}

