package com.cirogg.conexa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel(){

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> get() = _newsList

    private val _newsDetail = MutableStateFlow<News?>(null)
    val newsDetail: StateFlow<News?> get() = _newsDetail

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val news = newsRepository.fetchNews()
                _newsList.value = news
                Log.d("NewsViewModel", "Fetched news: $news")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("NewsViewModel", "Error fetching news", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchNewsById(newsId: String) {
        viewModelScope.launch {
            try {
                val news = newsRepository.getNewsById(newsId)
                _newsDetail.value = news
                Log.d("NewsViewModel", "Fetched news detail: $news")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("NewsViewModel", "Error fetching news detail", e)
            }
        }
    }

}