package com.cirogg.conexa.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cirogg.conexa.R
import com.cirogg.conexa.data.model.Address
import com.cirogg.conexa.data.model.Geo
import com.cirogg.conexa.data.model.Users
import com.cirogg.conexa.viewmodel.UsersViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

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
            .padding(16.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "User data:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "First Name: ${user.firstname}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "Last Name: ${user.lastname}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Address:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Street: ${user.address.street}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "Suite: ${user.address.suite}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "City: ${user.address.city}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "Zipcode: ${user.address.zipcode}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            MapComponent(user = user)
        }
    }
}

@Composable
fun MapComponent(user: Users) {

    val userLocation = LatLng(
        user.address.geo.lat.toDoubleOrNull() ?: 0.0,
        user.address.geo.lng.toDoubleOrNull() ?: 0.0
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 15f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = userLocation),
            title = "One Marker",
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_conexa),
        )

    }
}

@Composable
@Preview
fun PreviewUserDetailScreen() {
    val mockUsers = Users(
        id = "1",
        firstname = "John",
        lastname = "Doe",
        email = "john.doe@example.com",
        address = Address(
            street = "123 Main St",
            suite = "Apt 4",
            city = "Anytown",
            zipcode = "12345",
            geo = Geo(
                lat = "40.7128",
                lng = "-74.0060"
            )
        )
    )

    UserDetailContent(user = mockUsers)
}