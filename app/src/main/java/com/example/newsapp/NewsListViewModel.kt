package com.example.newsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.Locale.Category


private const val TAG = "FetchNews"

class NewsListViewModel : ViewModel() {
    private val _news = MutableLiveData<List<NewsApiService.News>>()
    val news: LiveData<List<NewsApiService.News>> = _news

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // ApiService interface
    private val apiCall = retrofit.create(NewsApiCall::class.java)

    init {
        viewModelScope.launch {
            fetchNews("technology")
        }
    }

    private suspend fun fetchNews(category: String) {
        try {
            val response = withContext(Dispatchers.IO) {
                apiCall.getTopNewsByCategory(category).execute()
            }
            if (response.isSuccessful) {
                val articles = response.body()?.articles
                articles?.let {
                    _news.postValue(it)
                    Log.d(TAG, "articles: $it")
                } ?: run {
                    Log.e(TAG, "Articles list is null or empty")
                }
            } else {
                // Handle unsuccessful response
                Log.e(TAG, "Failed to fetch news: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            // Handle network errors
            Log.e(TAG, "Encountered error: ", e)
        }
    }
}