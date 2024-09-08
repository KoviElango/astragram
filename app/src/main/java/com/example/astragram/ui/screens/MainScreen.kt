package com.example.astragram.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astragram.R
import com.example.astragram.ui.screens.category.CategoryScreen
import com.example.astragram.ui.screens.favourites.FavoritesScreen
import com.example.astragram.ui.screens.home.HomeScreen
import com.example.astragram.viewmodel.HomeViewModel

//This is the Logo font, can be changed to any font you want but please create a new variable for additional fonts
val NasalizationFontFamily = FontFamily(
    Font(R.font.nasalization_rg, FontWeight.Normal)
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(mainViewModel: HomeViewModel = viewModel()) {
    val images by mainViewModel.imagesLiveData.observeAsState(emptyList())
    val errorMessage by mainViewModel.errorMessage.observeAsState()
    val favoriteStateMap = remember { mutableStateMapOf<String, Boolean>() }

    var selectedScreen by remember { mutableStateOf("Category") }
    val isLoading by mainViewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        mainViewModel.fetchImages()
    }


   //Color for the Scaffold icons
    val color1 = Color(0xFF00FFD9)
    val color2 = Color(0xFF537A75)

    Scaffold(
        modifier = Modifier.background(Color.Black),
        topBar = {
            TopAppBar(
                title = {
                    Text("ASTRAgram",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = NasalizationFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp)
                    )
                        },
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(60.dp),
                containerColor = Color.Black,
                content = {
                    NavigationBar(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ) {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Category",
                                    tint = if (selectedScreen == "Category") color1 else color2,
                                    modifier = Modifier.size(if (selectedScreen == "Category") 30.dp else 20.dp))
                            },
                            selected = selectedScreen == "Category",
                            onClick = { selectedScreen = "Category" },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Default.Public,
                                    contentDescription = "Home",
                                    tint = if (selectedScreen == "Home") color1 else color2,
                                    modifier = Modifier.size(if (selectedScreen == "Home") 30.dp else 20.dp))
                            },
                            selected = selectedScreen == "Home",
                            onClick = { selectedScreen = "Home" },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Favourites",
                                    tint = if (selectedScreen == "Favourites") color1 else color2,
                                    modifier = Modifier.size(if (selectedScreen == "Favourites") 30.dp else 20.dp))
                            },
                            selected = selectedScreen == "Favourites",
                            onClick = { selectedScreen = "Favourites" },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                /**
                 * AnimatedContent is used to animate the changes between the screens.
                 * added a delay of 500ms between the screens so the animation is visible
                 * Navigator between Home and Favourites
                 */

                AnimatedContent(
                    targetState = selectedScreen,
                    transitionSpec = {
                        if (targetState == "Category") {
                            slideInHorizontally(
                                animationSpec = tween(durationMillis = 500),
                                initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn() with
                                    slideOutHorizontally(
                                        animationSpec = tween(durationMillis = 500),
                                        targetOffsetX = { fullWidth -> fullWidth }) + fadeOut() using
                                    SizeTransform(clip = false)
                        } else {
                            slideInHorizontally(
                                animationSpec = tween(durationMillis = 500),
                                initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() with
                                    slideOutHorizontally(
                                        animationSpec = tween(durationMillis = 500),
                                        targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut() using
                                    SizeTransform(clip = false)
                        }
                    }
                ) { targetScreen ->
                    when (targetScreen) {
                        "Category" -> CategoryScreen(
                            viewModel = mainViewModel,
                            onCategorySelected = { selectedScreen = "Home" })
                        "Home" -> HomeScreen(
                            images = images,
                            errorMessage = errorMessage,
                            favoriteStateMap = favoriteStateMap,
                            onLoadMore = { mainViewModel.loadMoreImages() },
                            isLoading = isLoading,
                        )
                        "Favourites" -> FavoritesScreen()
                    }
                }
            }
        }
    )
}