package com.example.newsapp.getnews.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.getnews.data.model.Result
import com.example.newsapp.getnews.data.model.NewsArticle
import com.example.newsapp.getnews.data.repository.NewsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _news = MutableStateFlow<List<NewsArticle>>(emptyList())
    val news = _news.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private var pageOfApi = 0

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        onLoadNewsClicked()
    }

    fun onLoadNewsClicked() {
        getNews(pageOfApi)
    }

    fun getNews(page: Int) {
        viewModelScope.launch {
            newsRepository.getNewsList(page).collectLatest { result ->
                when(result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                        _isLoading.update {
                            false
                        }

                    }
                    is Result.Loading -> {
                        result.message?.let { message ->
                            _isLoading.update {
                                true
                            }
                        }

                    }
                    is Result.Success -> {
                        pageOfApi += 1
                        result.data?.let { newsArticles ->
                            _news.update { newsArticles }
                            _isLoading.update {
                                false
                            }
                        }
                    }
                }
            }
        }
    }

}