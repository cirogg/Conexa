package com.cirogg.conexa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cirogg.conexa.data.model.news.News
import com.cirogg.conexa.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> get() = _newsList

    private val _newsDetail = MutableStateFlow<News?>(null)
    val newsDetail: StateFlow<News?> get() = _newsDetail

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _filteredNewsList = MutableStateFlow<List<News>>(emptyList())
    val filteredNewsList: StateFlow<List<News>> get() = _filteredNewsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    init {
        viewModelScope.launch {
            fetchNews()
            _searchQuery.collect { query ->
                filterNews(query)
            }
        }
    }

    private fun filterNews(query: String) {
        _filteredNewsList.value = if (query.isEmpty()) {
            _newsList.value
        } else {
            _newsList.value.filter {
                it.title.contains(query, ignoreCase = true)
                        || it.content.contains(query, ignoreCase = true)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setNewsList(newsList: List<News>) {
        _newsList.value = newsList
    }

    fun fetchNews() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val news = newsRepository.fetchNews()
                _newsList.value = news
                filterNews(_searchQuery.value)
            } catch (e: Exception) {
                _errorMessage.value = e.message
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
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

}