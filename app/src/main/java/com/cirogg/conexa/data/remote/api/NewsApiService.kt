package com.cirogg.conexa.data.remote.api

import com.cirogg.conexa.data.model.News
import retrofit2.http.GET

interface NewsApiService {
    @GET("posts")
    suspend fun getNews(): List<News>
}