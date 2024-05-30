package com.cirogg.conexa.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cirogg.conexa.viewmodel.UsersViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.cirogg.conexa.R
import com.cirogg.conexa.data.model.Address
import com.cirogg.conexa.data.model.Geo
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onUserSelected(user.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user.firstname} ${user.lastname}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Image(
                modifier = Modifier.weight(0.2f).align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_map),
                contentDescription = "Map icon"
            )
        }
    }
}

@Composable
@Preview
fun PreviewUsersScreen() {
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

    UserListItem(
        user = mockUsers,
        onUserSelected = {}
    )
}
