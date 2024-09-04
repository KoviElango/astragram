package com.example.astragram

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.astragram.data.FavoriteImage
import com.example.astragram.viewmodel.FavoritesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setUp() {
        viewModel = FavoritesViewModel()
    }

    @Test
    fun `removeFromFavorites removes favorite image`() = runTest {
        val favoriteImage = FavoriteImage("path/to/image", "Image Title", "Image Description")
        viewModel.setInitialFavoritesForTest(listOf(favoriteImage))

        viewModel.removeFromFavorites(favoriteImage)
        advanceUntilIdle()

        kotlin.test.assertEquals(emptyList<FavoriteImage>(), viewModel.favoritesLiveData.value)
    }
}
