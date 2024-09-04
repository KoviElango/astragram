package com.example.astragram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.astragram.data.DisplayData

@Composable
fun ContentDialog(displayData: DisplayData, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = displayData.title)
        },
        text = {
            Column {
                AsyncImage(
                    model = displayData.url,
                    contentDescription = displayData.title,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(text = displayData.description)
            }
        },
        confirmButton = {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = "Close",
                    tint = androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .scale(scaleX = -1f, scaleY = 1f)
                )
            }
        }
    )
}
