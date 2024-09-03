package com.example.astragram.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astragram.network.NasaImageResponse
import com.example.astragram.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val imageUrlsLiveData = MutableLiveData<List<String>>() // Holds the list of image URLs
    val errorMessage = MutableLiveData<String>() //only for error messages

    // Function to fetch images from NASA API
    fun fetchImages(query: String = "nebula") {
        viewModelScope.launch {
            val call = RetrofitInstance.api.searchImages(query)

            call.enqueue(object : Callback<NasaImageResponse> {
                override fun onResponse(call: Call<NasaImageResponse>, response: Response<NasaImageResponse>) {
                    if (response.isSuccessful) {
                        val items = response.body()?.collection?.items ?: emptyList()

                        val imageUrls = items.flatMap { item ->
                            item.links?.filter { link -> link.rel == "preview" }?.map { link -> link.href } ?: emptyList()
                        }

                        imageUrlsLiveData.value = imageUrls
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