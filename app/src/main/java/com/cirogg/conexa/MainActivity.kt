package com.cirogg.conexa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cirogg.conexa.navigation.Navigator
import com.cirogg.conexa.navigation.NewsScreenNav
import com.cirogg.conexa.navigation.UsersScreenNav
import com.cirogg.conexa.ui.theme.ConexaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Conexa)
        setContent {

            val navController = rememberNavController()

            var showBottom by remember { mutableStateOf(true) }
            var selectedItem by remember { mutableIntStateOf(0) }
            val items = listOf("News", "Users")

            LaunchedEffect(navController) {
                navController.currentBackStackEntryFlow.collect { backStackEntry ->
                    when (navController.currentDestination?.route) {
                        NewsScreenNav.route -> {
                            selectedItem = 0
                            showBottom = true
                        }

                        UsersScreenNav.route -> {
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
                            NavigationBar {
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
                        Navigator(navController = navController)
                    }
                }
            }
        }
    }
}
