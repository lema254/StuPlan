package com.example.stuplan.ui.theme.screens.contentscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PythonScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val pythonModules = listOf(
        HtmlModule(1, "Python Basics", "Variables, data types, and basic syntax", "Beginner", "2 hours", isCompleted = true, progress = 1.0f),
        HtmlModule(2, "Control Flow", "Conditionals, loops, and logic", "Beginner", "2.5 hours", isCompleted = true, progress = 1.0f),
        HtmlModule(3, "Functions & Modules", "Defining and using functions, modules, and packages", "Intermediate", "3 hours", progress = 0.6f),
        HtmlModule(4, "Object-Oriented Programming", "Classes, objects, inheritance", "Intermediate", "4 hours", progress = 0.3f),
        HtmlModule(5, "File Handling", "Read and write to files", "Intermediate", "2 hours", progress = 0.1f),
        HtmlModule(6, "Python Libraries", "Intro to NumPy, Pandas, and Matplotlib", "Advanced", "4 hours", isLocked = true),
        HtmlModule(7, "Web Development with Flask", "Build a mini web app with Flask", "Advanced", "5 hours", isLocked = true)
    )

    val pythonProjects = listOf(
        HtmlProject("To-Do App", "Simple task tracker using Python and Tkinter", "Beginner", 4, isFeatured = true),
        HtmlProject("Budget Tracker", "Track expenses using Python and CSV", "Intermediate", 5),
        HtmlProject("Weather App", "Fetch live weather data with API", "Intermediate", 5),
        HtmlProject("Blog with Flask", "Build a full-featured blog using Flask", "Advanced", 7)
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
                            Icon(Icons.Default.Code, contentDescription = "Python", tint = White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Python Learning", fontWeight = FontWeight.SemiBold, color = TextDark)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark)
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
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
                        Text("Python Course Progress", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Row {
                            Icon(Icons.Default.Star, contentDescription = "XP", tint = PrimaryBlue)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("500 XP", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = 0.6f,
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
                        Text("60% complete", fontSize = 14.sp, color = TextDark)
                        Text("3/7 modules completed", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { },
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
                    items(pythonModules) { ModuleCard(
                        it,
                        onClick = TODO()
                    ) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
                1 -> LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    items(pythonProjects) { ProjectCard(it) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}