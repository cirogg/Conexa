package com.cirogg.conexa.data.model.news

import kotlinx.serialization.Serializable

@Serializable
class News(
    val id: String,
    val title: String,
    val content: String,
    val image: String,
    val thumbnail: String,
    val publishedAt: String
)