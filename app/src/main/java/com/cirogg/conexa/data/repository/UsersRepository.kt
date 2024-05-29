package com.cirogg.conexa.data.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.model.Users
import com.cirogg.conexa.data.model.UsersDao
import com.cirogg.conexa.data.model.toEntity
import com.cirogg.conexa.data.remote.api.UsersApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersRepository (
    private val usersApiService: UsersApiService,
    private val usersDao: UsersDao
) {

    suspend fun fetchUsers(): List<Users> {
        return withContext(Dispatchers.IO) {
            val usersFromApi = usersApiService.getUsers()
            usersDao.insertAll(usersFromApi.map { it.toEntity() })
            usersFromApi
        }
    }

    suspend fun getUserById(userId: String): Users? {
        return withContext(Dispatchers.IO) {
            usersDao.getUserById(userId)?.toUsers()
        }
    }

}