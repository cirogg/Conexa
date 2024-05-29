package com.cirogg.conexa.data.repository

import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.model.NewsDao
import com.cirogg.conexa.data.model.toEntity
import com.cirogg.conexa.data.remote.api.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao
) {

    suspend fun fetchNews(): List<News> {
        return withContext(Dispatchers.IO) {
            val newsFromApi = newsApiService.getNews()
            newsDao.insertAll(newsFromApi.map { it.toEntity() })
            newsFromApi
        }
    }

    suspend fun getNewsById(newsId: String): News? {
        return withContext(Dispatchers.IO) {
            newsDao.getNewsById(newsId)?.toNews()
        }
    }

}