package com.example.stuplan.ui.theme.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Web
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Define our theme colors to match the reference design
val PrimaryOrange = Color(0xFFFF6B35)
val BackgroundPeach = Color(0xFFFFE0D0)
val TextDark = Color(0xFF1D1D1D)
val White = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val scrollState = rememberScrollState() // Explicitly create a scroll state

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
                            text = "Code Academy",
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("profile")
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = TextDark
                        )
                    }
                    IconButton(onClick = {
                        /* open menu drawer or navigate */
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
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
        // Use Box to ensure proper filling of space
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPeach)
                .padding(innerPadding)
        ) {
            // Main scrollable content column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState) // Use the explicit scroll state
            ) {
                // User progress card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // User avatar
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryOrange.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "User",
                                    tint = PrimaryOrange,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "Welcome back, Alex!",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )

                                Text(
                                    text = "Continue your coding journey",
                                    fontSize = 14.sp,
                                    color = TextDark.copy(alpha = 0.7f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Progress bar
                        Text(
                            text = "Your overall progress",
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

                        Spacer(modifier = Modifier.height(24.dp))

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

                // Categories section
                Text(
                    text = "Programming Categories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Language category cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Language categories
                    CategoryCard(
                        name = "HTML",
                        icon = Icons.Default.Code,
                        color = Color(0xFF4E9DF5),
                        progress = 0.8f,
                        onClick = { navController.navigate("html_screen") }
                    )

                    CategoryCard(
                        name = "Kotlin",
                        icon = Icons.Default.Android,
                        color = PrimaryOrange,
                        progress = 0.65f,
                        onClick = { navController.navigate("kotlin_screen") }
                    )

                    CategoryCard(
                        name = "JavaScript",
                        icon = Icons.Default.Web,
                        color = Color(0xFFFFD60A),
                        progress = 0.4f,
                        onClick = { navController.navigate("javascript_screen") }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryCard(
                        name = "Python",
                        icon = Icons.Default.Settings,
                        color = Color(0xFF58C27D),
                        progress = 0.2f,
                        onClick = { navController.navigate("python_screen") }
                    )

                    CategoryCard(
                        name = "Swift",
                        icon = Icons.Default.SwapHoriz,
                        color = Color(0xFFFF2D55),
                        progress = 0.1f,
                        onClick = { navController.navigate("swift_screen") }
                    )

                    CategoryCard(
                        name = "More",
                        icon = Icons.Default.Add,
                        color = Color(0xFF6C757D),
                        progress = null,
                        onClick = { navController.navigate("more_categories") }
                    )
                }

                // Recent Activity section
                Text(
                    text = "Recent Activity",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Recent activity list
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Activity item 1
                        ActivityItem(
                            title = "Kotlin Coroutines",
                            subtitle = "Advanced · Completed 2 hours ago",
                            icon = Icons.Default.Android,
                            progress = 1.0f,
                            iconColor = PrimaryOrange
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )

                        // Activity item 2
                        ActivityItem(
                            title = "JavaScript Promises",
                            subtitle = "Intermediate · Last session: Yesterday",
                            icon = Icons.Default.Web,
                            progress = 0.7f,
                            iconColor = Color(0xFFFFD60A)
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )

                        // Activity item 3
                        ActivityItem(
                            title = "HTML5 Canvas API",
                            subtitle = "Intermediate · Last session: 3 days ago",
                            icon = Icons.Default.Code,
                            progress = 0.4f,
                            iconColor = Color(0xFF4E9DF5)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Learning stats section
                Text(
                    text = "Your Learning Stats",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Stats cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Stats cards
                    StatCard(
                        number = "12",
                        text = "Courses in progress",
                        color = PrimaryOrange
                    )

                    StatCard(
                        number = "48",
                        text = "Hours learned",
                        color = Color(0xFF4E9DF5)
                    )

                    StatCard(
                        number = "86",
                        text = "Challenges completed",
                        color = Color(0xFF58C27D)
                    )
                }

                // Recommended next course
                Text(
                    text = "Recommended for you",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Recommended course card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Course thumbnail
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PrimaryOrange.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Android,
                                    contentDescription = "Course",
                                    tint = PrimaryOrange,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "RECOMMENDED",
                                        fontSize = 12.sp,
                                        color = PrimaryOrange,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .height(8.dp)
                                            .width(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF58C27D))
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "NEW",
                                        fontSize = 12.sp,
                                        color = Color(0xFF58C27D),
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Kotlin Multiplatform Mobile",
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark,
                                    fontSize = 16.sp
                                )

                                Text(
                                    text = "Advanced · 8 hours · 12 modules",
                                    color = TextDark.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Learn to build cross-platform apps for iOS and Android with Kotlin Multiplatform Mobile technology",
                            color = TextDark.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { /* Start course */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text(
                                    text = "Start Course",
                                    color = White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            OutlinedButton(
                                onClick = { /* Save for later */ },
                                modifier = Modifier.height(IntrinsicSize.Min),
                                shape = RoundedCornerShape(24.dp),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = SolidColor(PrimaryOrange)
                                )
                            ) {
                                Icon(
                                    Icons.Default.BookmarkBorder,
                                    contentDescription = "Save",
                                    tint = PrimaryOrange
                                )
                            }
                        }
                    }
                }

                // Additional spacing at the bottom to ensure content doesn't get cut off
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// Category card composable for programming languages
@Composable
fun CategoryCard(
    name: String,
    icon: ImageVector,
    color: Color,
    progress: Float?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = name,
                    tint = color,
                    modifier = Modifier.size(30.dp)
                )
            }

            // Language name
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            // Progress
            if (progress != null) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = color,
                    trackColor = Color.LightGray
                )
            }
        }
    }
}

// Activity item composable for recent activities
@Composable
fun ActivityItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    progress: Float,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Open activity */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Activity icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = subtitle,
                color = TextDark.copy(alpha = 0.7f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = iconColor,
                trackColor = Color.LightGray
            )
        }
    }
}

// Stat card composable for achievement metrics
@Composable
fun StatCard(number: String, text: String, color: Color) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = number,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                color = TextDark,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(navController = rememberNavController())
}