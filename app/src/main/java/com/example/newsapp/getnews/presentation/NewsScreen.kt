package com.example.newsapp.getnews.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.Screen
import com.example.newsapp.getnews.data.model.NewsArticle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel
) {

    val listState = rememberLazyListState()
// Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()

    val newsFromApi = viewModel.news.collectAsState().value
    var newsList = remember {
        mutableStateListOf<NewsArticle>()
    }

    newsList.addAll(newsFromApi)

    val context = LocalContext.current

    val isLoading = viewModel.isLoading.collectAsState().value

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column (
            modifier = Modifier
                    .fillMaxSize()
        ) {
            Text(
                text = "Nasa News",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                        .padding(16.dp, bottom = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 2.dp, color = Color.Black)
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(end = 4.dp),
                state = listState
            ) {
                items(newsList.size) { index ->

                    if (index != newsList.size - 1) {
                        NewsRow(newsList[index],
                            {
                                navController.navigate(Screen.NewsArticleScreen.route + "/${index}")
                            })
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(thickness = 2.dp, color = Color.Black)
                    } else {
                        NewsRow(newsList[index],
                            {
                                navController.navigate(Screen.NewsArticleScreen.route + "/${index}")
                            })
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(thickness = 2.dp, color = Color.Black)
                        Button(
                            onClick = {
                                viewModel.onLoadNewsClicked()
                                val lastItemIndex = newsList.size - 1
                                newsList.addAll(newsFromApi)
                                coroutineScope.launch {
                                    listState.animateScrollToItem(lastItemIndex)
                                }
                            },
                            modifier = Modifier
                                    .padding(16.dp)

                        ) {
                            Text(text = "Load more")
                        }
                    }
                }

            }




        }
        
    }
}


@Composable
fun NewsRow(
    newsArticle: NewsArticle,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column (
            modifier = Modifier
                    .weight(0.8f)
        )  {
            Text(
                text = "${newsArticle.title}",
                modifier = Modifier
                        .padding(16.dp, bottom = 0.dp),
            )

            Text(
                text = "${newsArticle.date}",
                modifier = Modifier
                        .padding(start = 16.dp, bottom = 8.dp),
                fontSize = 11.sp
            )
        }



        Icon(
            Icons.Filled.KeyboardArrowRight,
            contentDescription = "go to article",
            modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(0.2f)
        )
        

    }
}