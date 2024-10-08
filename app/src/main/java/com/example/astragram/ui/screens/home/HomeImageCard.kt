package com.example.astragram.ui.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.astragram.data.DisplayData
import com.example.astragram.ui.screens.ContentDialog
import com.example.astragram.ui.screens.ExpandableText


//Outline of the image card; Consists of imageItem and the two buttons

@Composable
fun ImageCard(
    displayData: DisplayData,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    val showDetail = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.Black.copy(alpha = 0.5f))

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

    // Show the content dialog when the button is clicked
    if (showDetail.value) {
        ContentDialog(displayData, onDismiss = { showDetail.value = false })
    }
}

//ImageItem is used to paint and format the image with the title and description

@Composable
fun ImageItem(displayData: DisplayData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = displayData.url,
            contentDescription = displayData.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = displayData.title,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White), fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp)
        )

        ExpandableText(displayData.description)
    }
}

//Buttons - Both buttons are two seperate composable because they could be gamified if needed

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
            tint = if (showDetail) Color.Blue else Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun FavouriteButton(
    isFavourite: Boolean,
    onClick: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    val tint by animateColorAsState(targetValue = if (isFavourite) Color.Red else Color.White,
        label = "User Clicked Favourite Button"
    )

    LaunchedEffect(isFavourite) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
        )
    }

    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favourite",
            tint = tint,
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
    }
}
