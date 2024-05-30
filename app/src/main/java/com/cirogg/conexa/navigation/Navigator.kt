package com.cirogg.conexa.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cirogg.conexa.ui.screen.NewsDetailScreen
import com.cirogg.conexa.ui.screen.NewsScreen
import com.cirogg.conexa.ui.screen.UserDetailScreen
import com.cirogg.conexa.ui.screen.UsersScreen
import com.cirogg.conexa.viewmodel.NewsViewModel
import com.cirogg.conexa.viewmodel.UsersViewModel
import kotlinx.serialization.Serializable

@Composable
fun Navigator(
    navController: NavHostController
) {

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


@Serializable
object NewsScreenNav{
    const val route = "com.cirogg.conexa.navigation.NewsScreenNav"
}

@Serializable
data class NewsDetailScreenNav(
    val newsId: String
)

@Serializable
object UsersScreenNav{
    const val route = "com.cirogg.conexa.navigation.UsersScreenNav"
}

@Serializable
data class UsersDetailScreenNav(
    val userId: String
)