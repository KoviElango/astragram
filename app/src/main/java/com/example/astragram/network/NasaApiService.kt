package com.example.astragram.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface NasaApiService {
    @GET("/search")
    fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String = "image"
    ): Call<NasaImageResponse>
}