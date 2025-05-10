package com.example.stuplan.ui.theme.screens.contentscreens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.stuplan.ui.theme.screens.contentscreens.PrimaryBlue
import com.example.stuplan.ui.theme.screens.contentscreens.BackgroundPeach
import com.example.stuplan.ui.theme.screens.contentscreens.TextDark
import com.example.stuplan.ui.theme.screens.contentscreens.White
import com.example.stuplan.ui.theme.screens.contentscreens.HtmlModule
import com.example.stuplan.ui.theme.screens.contentscreens.HtmlProject
import com.example.stuplan.ui.theme.screens.contentscreens.ModuleCard
import com.example.stuplan.ui.theme.screens.contentscreens.ProjectCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwiftScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val swiftModules = listOf(
        HtmlModule(1, "Swift Basics", "Syntax, variables, and data types", "Beginner", "2 hours", isCompleted = true, progress = 1.0f),
        HtmlModule(2, "Control Flow", "Conditionals, loops, and switch statements", "Beginner", "2.5 hours", isCompleted = true, progress = 1.0f),
        HtmlModule(3, "Functions & Closures", "Defining functions and using closures", "Intermediate", "3 hours", progress = 0.6f),
        HtmlModule(4, "SwiftUI Fundamentals", "Building UI with SwiftUI views", "Intermediate", "4 hours", progress = 0.3f),
        HtmlModule(5, "Async/Await", "Concurrency with async/await", "Advanced", "3 hours", isLocked = true),
        HtmlModule(6, "iOS App Lifecycle", "App lifecycle and scene management", "Advanced", "2 hours", isLocked = true)
    )

    val swiftProjects = listOf(
        HtmlProject("Currency Converter", "Build a converter app using SwiftUI", "Beginner", 4, isFeatured = true),
        HtmlProject("To-Do App", "Task manager with Core Data", "Intermediate", 5),
        HtmlProject("Portfolio App", "Showcase projects with SwiftUI", "Advanced", 6)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Code, contentDescription = "Swift", tint = White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Swift Learning", fontWeight = FontWeight.SemiBold, color = TextDark)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Bookmark */ }) {
                        Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Bookmark", tint = TextDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundPeach)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPeach)
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Swift Course Progress", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Row {
                            Icon(Icons.Default.Star, contentDescription = "XP", tint = PrimaryBlue)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("380 XP", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = 0.63f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        color = PrimaryBlue,
                        trackColor = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("63% complete", fontSize = 14.sp, color = TextDark)
                        Text("2/6 modules completed", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Continue */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Continue Learning", color = White, modifier = Modifier.padding(vertical = 8.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            TabRow(selectedTabIndex, containerColor = BackgroundPeach, contentColor = PrimaryBlue) {
                Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }, text = { Text("Learning Modules") })
                Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }, text = { Text("Projects") })
            }

            when (selectedTabIndex) {
                0 -> LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    items(swiftModules) { ModuleCard(
                        it,
                        onClick = TODO()
                    ) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
                1 -> LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    items(swiftProjects) { ProjectCard(it) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}