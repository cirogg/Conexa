package com.cirogg.conexa.data.repository

import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.remote.api.NewsApiService

class NewsRepository(private val newsApiService: NewsApiService) {

    suspend fun fetchNews(): List<News> {
        return newsApiService.getNews()
    }

}