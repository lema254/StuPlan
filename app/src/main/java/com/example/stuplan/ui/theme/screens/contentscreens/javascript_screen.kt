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
fun JavaScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val javaModules = listOf(
        HtmlModule(1, "Java Basics", "Syntax, variables, and data types", "Beginner", "2 hours", isCompleted = true, progress = 1.0f),
        HtmlModule(2, "OOP Principles", "Classes, objects, and inheritance", "Beginner", "3 hours", isCompleted = true, progress = 1.0f),
        HtmlModule(3, "Collections Framework", "Lists, Sets, Maps, and Queues", "Intermediate", "2.5 hours", progress = 0.7f),
        HtmlModule(4, "Exception Handling", "Try-catch, throws, and custom exceptions", "Intermediate", "2 hours", progress = 0.4f),
        HtmlModule(5, "Streams & Lambdas", "Functional programming with streams", "Advanced", "3 hours", isLocked = true)
    )

    val javaProjects = listOf(
        HtmlProject("Calculator App", "Build a basic calculator in Java", "Beginner", 2, isFeatured = true),
        HtmlProject("Inventory System", "Manage products with Java collections", "Intermediate", 4),
        HtmlProject("Chat Server", "Socket programming in Java", "Advanced", 6)
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
                            Icon(Icons.Default.Code, contentDescription = "Java", tint = White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Java Learning", fontWeight = FontWeight.SemiBold, color = TextDark)
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
                        Text("Java Course Progress", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Row {
                            Icon(Icons.Default.Star, contentDescription = "XP", tint = PrimaryBlue)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("720 XP", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = 0.75f,
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
                        Text("75% complete", fontSize = 14.sp, color = TextDark)
                        Text("3/4 modules completed", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
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
                    items(javaModules) { ModuleCard(
                        it,
                        onClick = TODO()
                    ) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
                1 -> LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    items(javaProjects) { ProjectCard(it) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}