package com.cirogg.conexa.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel(),
    onNewsSelected: (String) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val newsList by viewModel.filteredNewsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column {
        TextField(
            value = searchQuery,
            onValueChange = {
                viewModel.setSearchQuery(it)
            },
            label = { Text("Search news") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(newsList) { news ->
                    NewsItem(
                        news = news,
                        onNewsSelected = { onNewsSelected(news.id) }
                    )
                }
            }
        }
    }

}

@Composable
fun NewsItem(
    news: News,
    onNewsSelected: (String) -> Unit
){
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onNewsSelected(news.id) }) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = news.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Text(
                text = news.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Preview
@Composable
fun NewsScreenPreview() {
    //NewsScreen()
    NewsItem(
        News(
            id = "1",
            "Title",
            "Content",
            "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            "2021-09-01"
        )
    ) {

    }
}