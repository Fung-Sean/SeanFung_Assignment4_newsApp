package com.example.newsapp


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query



interface NewsApiCall {
    @GET("top-headlines?country=us&apiKey=ac65e52b1e4b43a99b7d86ec5efcf859")
    fun getTopNewsByCategory(@Query("category") category: String): Call<NewsApiService.ApiResponse>

}