package com.example.astragram.ui.screens.category


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astragram.ui.theme.BackgroundWrapper
import com.example.astragram.viewmodel.HomeViewModel

/**
 * This is the Category screen; contains a lazy list of buttons which is not the landing screen, users get to choose a category to search for images.
 * the default category would be nebula.
 * Category parameter is sent to viewmodel and stored in variable: _searchQuery
 */

@Composable
fun CategoryScreen(viewModel: HomeViewModel = viewModel(), onCategorySelected: () -> Unit) {

    val searchItems = listOf("nebula", "stars", "galaxy", "planet", "moon", "earth", "aliens")
    var selectedItem by remember { mutableStateOf(searchItems[0]) }
    BackgroundWrapper {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally    ) {

        item {
            Text(
                text = "Please select a category \uD83D\uDC69\uD83C\uDFFC\u200D\uD83D\uDE80",
                modifier = Modifier
                    .padding(20.dp),
                color = Color.White,
                fontSize = 16.sp
            )
        }
        items(searchItems) { item ->

            Button(
                onClick = {
                    selectedItem = item
                    viewModel.updateSearchQuery(item)
                    onCategorySelected()
                },
                modifier = Modifier
                    .padding(8.dp)
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (item == "aliens") Color(0f, 0.6f, 0f, 0.3f) else if (item == "earth") Color(0.5f, 0.8f, 1f, 0.3f) else Color(0.1f, 0.1f, 0.1f, 0.3f)
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                shape = RoundedCornerShape(17.dp)
            ) {
                Text(text = item)
            }
        }
    }
        }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {CategoryScreen(onCategorySelected = {})
}



