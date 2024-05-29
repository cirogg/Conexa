package com.cirogg.conexa.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val image: String,
    val thumbnail: String
) {
    fun toNews() = News(id, title, content, image, thumbnail)
}

fun News.toEntity() = NewsEntity(id, title, content, image, thumbnail)