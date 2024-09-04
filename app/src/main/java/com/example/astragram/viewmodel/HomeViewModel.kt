package com.example.astragram.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astragram.data.DisplayData
import com.example.astragram.network.NasaImageResponse
import com.example.astragram.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val imagesLiveData = MutableLiveData<List<DisplayData>>() // Holds the list of image URLs, titles, descriptions
    val errorMessage = MutableLiveData<String>() // Only for error messages

    // Function to fetch images from NASA API
    fun fetchImages(query: String = "nebula") {
        viewModelScope.launch {
            val call = RetrofitInstance.api.searchImages(query)

            call.enqueue(object : Callback<NasaImageResponse> {
                override fun onResponse(
                    call: Call<NasaImageResponse>,
                    response: Response<NasaImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body()?.collection?.items ?: emptyList()

                        // Extract the URLs, titles, and descriptions of the images
                        val imageList = items.mapNotNull { item ->
                            val url = item.links?.find { link -> link.rel == "preview" }?.href
                            val data = item.data?.firstOrNull()
                            val title = data?.title
                            val description = data?.description ?: "No description available"

                            if (url != null && title != null) {
                                // Create DisplayData with URL, title, and description
                                DisplayData(
                                    url = url,
                                    title = title,
                                    description = description,
                                    isFavorite = false
                                )
                            } else {
                                null
                            }
                        }

                        imagesLiveData.value = imageList
                    } else {
                        errorMessage.value = "Error: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<NasaImageResponse>, t: Throwable) {
                    errorMessage.value = "Failure: ${t.message}"
                }
            })
        }
    }
}
