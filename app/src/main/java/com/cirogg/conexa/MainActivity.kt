package com.cirogg.conexa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.ui.screen.NewsDetailScreen
import com.cirogg.conexa.ui.screen.NewsScreen
import com.cirogg.conexa.ui.screen.UserDetailScreen
import com.cirogg.conexa.ui.screen.UsersScreen
import com.cirogg.conexa.ui.theme.ConexaTheme
import com.cirogg.conexa.viewmodel.NewsViewModel
import com.cirogg.conexa.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Conexa)
        //enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            var showBottom by remember { mutableStateOf(true) }
            var selectedItem by remember { mutableIntStateOf(0) }
            val items = listOf("News", "Users")

            LaunchedEffect(navController) {
                navController.currentBackStackEntryFlow.collect { backStackEntry ->
                    when (navController.currentDestination?.route) {
                        "com.cirogg.conexa.NewsScreenNav" -> {
                            selectedItem = 0
                            showBottom = true
                        }

                        "com.cirogg.conexa.UsersScreenNav" -> {
                            selectedItem = 1
                            showBottom = true
                        }

                        else -> {
                            showBottom = false
                        }
                    }
                }
            }

            ConexaTheme {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    Text(text = items[selectedItem])
                                }
                            },
                        )

                    },
                    bottomBar = {
                        if (showBottom) {
                            NavigationBar (){
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                        ),
                                        icon = {
                                            when (index) {
                                                0 -> {
                                                    Icon(
                                                        Icons.Filled.Home,
                                                        contentDescription = item
                                                    )
                                                }

                                                1 -> {
                                                    Icon(
                                                        Icons.Filled.AccountBox,
                                                        contentDescription = item
                                                    )
                                                }
                                            }
                                        },
                                        label = { Text(item) },
                                        selected = selectedItem == index,
                                        onClick = {
                                            selectedItem = index
                                            when (selectedItem) {
                                                0 -> {
                                                    navController.navigate(NewsScreenNav) {
                                                        popUpTo(navController.graph.startDestinationId)
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }

                                                1 -> {
                                                    navController.navigate(UsersScreenNav) {
                                                        popUpTo(navController.graph.startDestinationId)
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ){
                        NavHost(
                            navController = navController,
                            startDestination = NewsScreenNav
                        ) {

                            composable<NewsScreenNav> {
                                val viewModel = hiltViewModel<NewsViewModel>()
                                NewsScreen(
                                    viewModel = viewModel,
                                    onNewsSelected = {
                                        navController.navigate(
                                            NewsDetailScreenNav(
                                                newsId = it
                                            )
                                        )
                                    })
                            }

                            composable<NewsDetailScreenNav> {
                                val args = it.toRoute<NewsDetailScreenNav>().newsId
                                val viewModel = hiltViewModel<NewsViewModel>()
                                NewsDetailScreen(
                                    viewModel = viewModel,
                                    newsId = args
                                )
                            }

                            composable<UsersScreenNav> {
                                val viewModel = hiltViewModel<UsersViewModel>()
                                UsersScreen(
                                    viewModel = viewModel,
                                    onUserSelected = {
                                        navController.navigate(
                                            UsersDetailScreenNav(
                                                userId = it
                                            )
                                        )
                                    }
                                )
                            }

                            composable<UsersDetailScreenNav> {
                                val args = it.toRoute<UsersDetailScreenNav>().userId
                                val viewModel = hiltViewModel<UsersViewModel>()
                                UserDetailScreen(
                                    viewModel = viewModel,
                                    userId = args
                                )
                            }

                        }
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

@Serializable
object UsersScreenNav

@Serializable
data class UsersDetailScreenNav(
    val userId: String
)