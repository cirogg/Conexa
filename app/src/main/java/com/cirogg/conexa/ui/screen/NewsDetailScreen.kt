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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cirogg.conexa.data.model.News

@Composable
fun NewsDetailScreen(newss: String) {

    val news = News(
        id = "1",
        title = "Sample News Title",
        content = "Sample news content...",
        image = "https://via.placeholder.com/300",
        thumbnail = "https://via.placeholder.com/150"
    )

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
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = news.content,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewNewsDetailScreen() {
    val news = News(
        id = "1",
        title = "Sample News Title",
        content = "Sample news content...",
        image = "https://via.placeholder.com/300",
        thumbnail = "https://via.placeholder.com/150"
    )
    NewsDetailScreen(newss = "news")
}