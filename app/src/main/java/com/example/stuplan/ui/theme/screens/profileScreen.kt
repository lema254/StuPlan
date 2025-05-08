package com.example.stuplan.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stuplan.data.UserViewModel
import com.example.stuplan.ui.theme.screens.dashboard.BackgroundPeach
import com.example.stuplan.ui.theme.screens.dashboard.PrimaryOrange
import com.example.stuplan.ui.theme.screens.dashboard.TextDark
import com.example.stuplan.ui.theme.screens.dashboard.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    val user = viewModel.user.value
    val scrollState = rememberScrollState()

    // If no user is logged in, redirect to register screen
    if (user == null) {
        LaunchedEffect(Unit) { navController.navigate("register") }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PrimaryOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SP",
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "My Profile",
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextDark
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("editProfile") }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = TextDark
                        )
                    }
                    IconButton(onClick = { /* Open settings */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = TextDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundPeach,
                    titleContentColor = TextDark
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPeach)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // User avatar and info card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // User avatar
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(PrimaryOrange.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "User",
                                tint = PrimaryOrange,
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // User name
                        Text(
                            text = user.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = user.email,
                            fontSize = 14.sp,
                            color = TextDark.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // User progress
                        Text(
                            text = "Overall Progress",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = 0.65f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = PrimaryOrange,
                            trackColor = Color.LightGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "65% complete",
                                fontSize = 14.sp,
                                color = TextDark
                            )

                            Text(
                                text = "Level 7",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryOrange
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { /* Resume learning */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                text = "Resume Learning",
                                color = White,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // User bio section
                ProfileSection(
                    title = "About Me",
                    icon = Icons.Default.Info
                ) {
                    Text(
                        text = if (user.bio.isNotBlank()) user.bio else "No bio provided",
                        fontSize = 16.sp,
                        color = TextDark,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Statistics section
                ProfileSection(
                    title = "Statistics",
                    icon = Icons.Default.Analytics
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatCard(
                            value = "48",
                            label = "Hours learned"
                        )
                        StatCard(
                            value = "12",
                            label = "Courses"
                        )
                        StatCard(
                            value = "86",
                            label = "Challenges"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
                OutlinedButton(
                    onClick = { navController.navigate("progress") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryOrange
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ShowChart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Detailed Progress")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { navController.navigate("achievements") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryOrange
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Achievements")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("editProfile") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Edit Profile",
                        color = White
                    )
                }

                // Additional spacing at the bottom
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ProfileSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Section header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryOrange
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
            }

            // Section divider
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = TextDark.copy(alpha = 0.1f)
            )

            // Section content
            content()
        }
    }
}

@Composable
fun StatCard(
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryOrange
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextDark.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfleScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}