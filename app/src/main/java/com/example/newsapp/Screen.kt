package com.example.newsapp

sealed class Screen (val route: String) {
    object MainScreen: Screen("main_screen")
    object NewsArticleScreen: Screen("news_article_screen")
}