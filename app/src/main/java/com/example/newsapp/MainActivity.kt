package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.getnews.data.remote.RetrofitInstance
import com.example.newsapp.getnews.data.NewsRepositoryImpl
import com.example.newsapp.getnews.presentation.NewsScreen
import com.example.newsapp.getnews.presentation.NewsViewModel
import com.example.newsapp.navigation.Navigation
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<NewsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NewsViewModel(NewsRepositoryImpl(RetrofitInstance.api)) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(viewModel = viewModel)
                }
            }
        }
    }
}

