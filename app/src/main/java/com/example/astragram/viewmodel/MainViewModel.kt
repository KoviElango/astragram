package com.example.astragram.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astragram.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val imageUrlLiveData = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun fetchImage(query: String = "nebula"){
        viewModelScope.launch {
            val call = RetrofitInstance.api.searchImages(query)
        }
    }

}