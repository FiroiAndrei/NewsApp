package com.example.newsapp.getnews.data.model

sealed class Result<T> (
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T> (data: T?): Result<T>(data)
    class Loading<T> (message: String?): Result<T>(message = "Loading...")
    class Error<T> (data: T?, message: String?): Result<T>(data, message)
}
