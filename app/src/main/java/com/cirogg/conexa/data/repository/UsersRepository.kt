package com.cirogg.conexa.data.repository

import com.cirogg.conexa.data.model.users.Users
import com.cirogg.conexa.data.model.users.UsersDao
import com.cirogg.conexa.data.model.users.toEntity
import com.cirogg.conexa.data.remote.api.UsersApiService
import kotlinx.coroutines.Dispatchers
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