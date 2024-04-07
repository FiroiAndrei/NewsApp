package com.example.newsapp.getnews.data.remote

import com.example.newsapp.getnews.data.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("news_items")
    suspend fun getNews(
        @Query("page") page: Int
    ): News

    companion object {
        const val BASE_URL = "https://mars.nasa.gov/api/v1/"
    }

}