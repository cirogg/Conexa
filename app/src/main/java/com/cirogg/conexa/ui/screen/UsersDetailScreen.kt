package com.cirogg.conexa.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cirogg.conexa.data.model.Users
import com.cirogg.conexa.viewmodel.UsersViewModel

@Composable
fun UserDetailScreen(
    userId: String,
    viewModel: UsersViewModel = viewModel()
) {
    val user by viewModel.userDetail.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUserById(userId)
    }

    user?.let { user ->
        UserDetailContent(user = user)
    } ?: run {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun UserDetailContent(user: Users) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "First Name: ${user.firstname}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Last Name: ${user.lastname}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Address:", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Street: ${user.address.street}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Suite: ${user.address.suite}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "City: ${user.address.city}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Zipcode: ${user.address.zipcode}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Geo Location:", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Lat: ${user.address.geo.lat}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Lng: ${user.address.geo.lng}", style = MaterialTheme.typography.bodyLarge)
    }
}