package com.example.newsapp.getnews.data.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("items")
    val newsArticles: List<NewsArticle>,
    val more: Boolean,
    val page: Int,
    val per_page: Int,
    val total: Int
)