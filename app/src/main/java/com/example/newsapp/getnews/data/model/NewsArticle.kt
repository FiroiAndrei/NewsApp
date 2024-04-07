package com.example.newsapp.getnews.data.model

data class NewsArticle(
    val body: String,
    val byline: String,
    val date: String,
    val description: String,
    val id: Int,
    val item_name: String,
    val main_image: String,
    val main_image_alt: String,
    val meta_author: String,
    val meta_keywords: String,
    val missions: List<List<Any>>,
    val news_type: List<List<Any>>,
    val status: Int,
    val target: String,
    val thumb: String,
    val title: String,
    val updated_at: String,
    val url: String
)