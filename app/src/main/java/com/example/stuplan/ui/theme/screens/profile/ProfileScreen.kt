import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.stuplan.ui.theme.screens.contentscreens.HTMLScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userId: String? = null,
    onNavigateToEdit: () -> Unit,
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isCurrentUser = userId == null // If userId is null, viewing own profile

    // Load user profile data
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile(userId)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(userProfile?.username ?: "Profile") },
                actions = {
                    if (isCurrentUser) {
                        IconButton(onClick = {  navController.navigate("edit_profile")}) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            userProfile?.let { profile ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Profile header with image and stats
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Profile image
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .border(1.dp, Color.Gray, CircleShape)
                        ) {
                            if (profile.profileImageUrl!!.isNotEmpty()) {
                                Image(
                                    painter = rememberImagePainter(profile.profileImageUrl),
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Default Profile",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center),
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Profile stats
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ProfileStat(count = profile.posts, label = "Posts")
                            ProfileStat(count = profile.followers, label = "Followers")
                            ProfileStat(count = profile.following, label = "Following")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Profile info
                    Text(
                        text = profile.displayName,
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (profile.bio.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = profile.bio,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    if (profile.website.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = profile.website,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons
                    if (isCurrentUser) {
                        Button(
                            onClick = onNavigateToEdit,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Edit Profile")
                        }
                    } else {
                        Button(
                            onClick = { /* Implement follow functionality */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Follow")
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    // Here you would add a grid of user posts or other profile content
                    Text(
                        text = "Posts will appear here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            // Loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ProfileStat(
    count: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(
        navController = rememberNavController(),
        userId = null,
        onNavigateToEdit = TODO(),
        viewModel = TODO(),
        onNavigateBack = TODO(),
    )}