package com.cirogg.conexa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cirogg.conexa.ui.screen.NewsScreen
import com.cirogg.conexa.ui.theme.ConexaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConexaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NewsScreenNav
                ) {
                    composable<NewsScreenNav> {
                        NewsScreen()
                    }
                    composable<NewsDetailScreenNav> {

                    }
                }
            }
        }
    }
}

@Serializable
object NewsScreenNav

@Serializable
data class NewsDetailScreenNav(
    val newsId: String
)