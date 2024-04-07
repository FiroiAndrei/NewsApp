package com.example.newsapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.Screen
import com.example.newsapp.getnews.presentation.NewsArticleScreen
import com.example.newsapp.getnews.presentation.NewsScreen
import com.example.newsapp.getnews.presentation.NewsViewModel

@Composable
fun Navigation(
    viewModel: NewsViewModel
) {
    val navController = rememberNavController()
    val newsList = viewModel.news.collectAsState().value
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            NewsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.NewsArticleScreen.route + "/{index}" ,
            arguments = listOf(
                navArgument("index") {
                type = NavType.IntType
                }

            )
        ) {entry ->
             NewsArticleScreen(
                 title = newsList[entry.arguments!!.getInt("index")].title,
                 body = newsList[entry.arguments!!.getInt("index")].body,
                 navController = navController
             )

        }

    }
}