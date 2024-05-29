package com.cirogg.conexa.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.cirogg.conexa.viewmodel.UsersViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cirogg.conexa.data.model.Users

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = viewModel(),
    onUserSelected: (String) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column {
        if (isLoading) {
            Text("Loading users...")
        } else if (errorMessage != null) {
            Text("Error: $errorMessage")
        } else {
            val usersList by viewModel.usersList.collectAsState()

            LazyColumn {
                items(usersList) { user ->
                    UserListItem(
                        user = user,
                        onUserSelected = { onUserSelected(user.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun UserListItem(
    user: Users,
    onUserSelected: (String) -> Unit
) {
    Card(modifier = Modifier.clickable { onUserSelected(user.id) }) {
        Column {
            Text(user.firstname)
            Text(user.lastname)
            Text(user.email)
        }
    }
}