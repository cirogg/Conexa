package com.cirogg.conexa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.model.Users
import com.cirogg.conexa.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
): ViewModel() {

    private val _usersList = MutableStateFlow<List<Users>>(emptyList())
    val usersList: StateFlow<List<Users>> get() = _usersList

    private val _userDetail = MutableStateFlow<Users?>(null)
    val userDetail: StateFlow<Users?> get() = _userDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    init {
        fetchUsers()
    }
    private fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val users = usersRepository.fetchUsers()
                _usersList.value = users
                Log.d("UsersViewModel", "Fetched users: $users")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("UsersViewModel", "Error fetching users", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserById(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val user = usersRepository.getUserById(userId)
                _userDetail.value = user
                Log.d("UsersViewModel", "Fetched user: $user")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("UsersViewModel", "Error fetching user", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

}