package com.example.astragram.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow

//This is to change read more or read less button int the description of the image

@Composable
fun ExpandableText(displayData: String) {
    val isExpanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 4.dp)) {
        Text(
            text = displayData,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
            maxLines = if (isExpanded.value) Int.MAX_VALUE else 3,
            overflow = if (isExpanded.value) TextOverflow.Visible else TextOverflow.Ellipsis,
            modifier = Modifier
                .clickable { isExpanded.value = !isExpanded.value }
        )

        Text(
            text = if (isExpanded.value) "Show less" else "Show more",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Cyan),
            modifier = Modifier
                .clickable { isExpanded.value = !isExpanded.value }
                .padding(top = 4.dp)
        )
    }
}

