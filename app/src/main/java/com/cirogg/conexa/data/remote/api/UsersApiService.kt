package com.cirogg.conexa.data.remote.api

import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.model.Users
import retrofit2.http.GET

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(): List<Users>
}