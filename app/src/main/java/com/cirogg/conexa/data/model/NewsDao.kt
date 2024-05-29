package com.cirogg.conexa.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id = :newsId")
    suspend fun getNewsById(newsId: String): NewsEntity?
}