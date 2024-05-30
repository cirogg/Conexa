package com.cirogg.conexa.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.viewmodel.NewsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsDetailScreen(
    viewModel: NewsViewModel = viewModel(),
    newsId: String
) {

    val newsDetail = viewModel.newsDetail.collectAsState()

    LaunchedEffect(newsId) {
        viewModel.fetchNewsById(newsId)
    }

    newsDetail.value?.let { news ->
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = news.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = news.publishedAt,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    } ?: run {
        Text("Noticia no encontrada", style = MaterialTheme.typography.titleSmall)
    }
}

@Preview
@Composable
fun PreviewNewsDetailScreen() {
    val sampleNews = News(
        id = "1",
        title = "Sample News Title",
        content = "Sample news content...",
        image = "https://via.placeholder.com/300",
        thumbnail = "https://via.placeholder.com/150",
        publishedAt = "2021-09-01"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = rememberAsyncImagePainter(model = sampleNews.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = sampleNews.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = sampleNews.content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = sampleNews.publishedAt,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}