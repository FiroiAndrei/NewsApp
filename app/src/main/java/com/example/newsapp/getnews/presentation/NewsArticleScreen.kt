package com.example.newsapp.getnews.presentation

import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.newsapp.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleScreen (
    title: String,
    body: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
   Scaffold (
       topBar = {
           TopAppBar(
               title = {
               Text(text = title)
               },
               navigationIcon = {
                   if (navController.previousBackStackEntry != null) {
                       IconButton(onClick = {
                           navController.navigateUp()
                       }) {
                           Icon(imageVector = Icons.Filled.ArrowBack , contentDescription = "Back")
                       }
                   }
               }
           )
       },
       content = {innerPadding ->
           Column (
               modifier = Modifier
                       .padding(innerPadding)
           ) {
               HtmlText(html = body, modifier = Modifier
                       .verticalScroll(rememberScrollState())
                       .padding(16.dp))
           }
       }
   ) 
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}