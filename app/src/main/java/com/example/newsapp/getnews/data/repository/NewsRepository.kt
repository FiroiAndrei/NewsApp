package com.example.newsapp.getnews.data.repository

import com.example.newsapp.getnews.data.model.Result
import com.example.newsapp.getnews.data.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(page: Int): Flow<Result<List<NewsArticle>>>
}