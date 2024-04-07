package com.example.newsapp.getnews.data

import android.util.Log
import com.example.newsapp.getnews.data.model.News
import com.example.newsapp.getnews.data.model.NewsArticle
import com.example.newsapp.getnews.data.model.Result
import com.example.newsapp.getnews.data.remote.Api
import com.example.newsapp.getnews.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val api: Api
): NewsRepository {
    override suspend fun getNewsList(page: Int): Flow<Result<List<NewsArticle>>> {
        return flow {
            val newsFromApi: News = try {
                emit(Result.Loading(message = "Loading..."))
                api.getNews(page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error loading news"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "No internet connection"))
                return@flow
            }
//            } catch (e:Exception) {
//                e.printStackTrace()
//                emit(Result.Error(data = null, message = "Unknown error"))
//            }

            emit(Result.Success(newsFromApi.newsArticles))
            return@flow

        }

    }

}