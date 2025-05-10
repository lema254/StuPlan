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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Using the same theme colors from your Dashboard
val PrimaryBlue = Color(0xFF4E9DF5)  // HTML specific color
val BackgroundPeach = Color(0xFFFFE0D0)
val TextDark = Color(0xFF1D1D1D)
val White = Color.White

data class HtmlModule(
    val id: Int,
    val title: String,
    val description: String,
    val difficulty: String,
    val duration: String,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val progress: Float = 0f
)

data class HtmlProject(
    val title: String,
    val description: String,
    val difficulty: String,
    val estimatedHours: Int,
    val isFeatured: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HTMLScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Sample HTML modules data
    val htmlModules = remember {
        listOf(
            HtmlModule(
                id = 1,
                title = "HTML Fundamentals",
                description = "Learn the basic structure of HTML documents and essential tags",
                difficulty = "Beginner",
                duration = "2 hours",
                isCompleted = true,
                progress = 1.0f

            ),
            HtmlModule(
                id = 2,
                title = "HTML5 Semantic Elements",
                description = "Understand how to structure your content with semantic HTML5 elements",
                difficulty = "Beginner",
                duration = "3 hours",
                isCompleted = true,
                progress = 1.0f
            ),
            HtmlModule(
                id = 3,
                title = "HTML Forms & Inputs",
                description = "Create interactive forms with various input types",
                difficulty = "Intermediate",
                duration = "4 hours",
                progress = 0.65f
            ),
            HtmlModule(
                id = 4,
                title = "HTML Tables & Lists",
                description = "Format and display tabular data and ordered/unordered lists",
                difficulty = "Beginner",
                duration = "2.5 hours",
                progress = 0.4f
            ),
            HtmlModule(
                id = 5,
                title = "Multimedia & Embedding",
                description = "Add images, audio, video and embed external content",
                difficulty = "Intermediate",
                duration = "3.5 hours",
                progress = 0.1f
            ),
            HtmlModule(
                id = 6,
                title = "HTML5 Canvas API",
                description = "Create graphics and animations with the Canvas API",
                difficulty = "Advanced",
                duration = "5 hours",
                isLocked = true
            ),
            HtmlModule(
                id = 7,
                title = "HTML Accessibility",
                description = "Learn ARIA roles and building accessible websites",
                difficulty = "Advanced",
                duration = "4 hours",
                isLocked = true
            )
        )
    }

    // Sample HTML projects
    val htmlProjects = remember {
        listOf(
            HtmlProject(
                title = "Personal Portfolio",
                description = "Build a responsive portfolio website to showcase your skills",
                difficulty = "Intermediate",
                estimatedHours = 6,
                isFeatured = true
            ),
            HtmlProject(
                title = "Blog Template",
                description = "Create a blog layout with articles and sidebar",
                difficulty = "Beginner",
                estimatedHours = 4
            ),
            HtmlProject(
                title = "Landing Page",
                description = "Design a product landing page with call-to-action elements",
                difficulty = "Intermediate",
                estimatedHours = 5
            ),
            HtmlProject(
                title = "Interactive Form",
                description = "Build a multi-step form with validation",
                difficulty = "Advanced",
                estimatedHours = 8
            )
        )
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
                                .background(PrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Code,
                                contentDescription = "HTML",
                                tint = White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "HTML Learning",
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
                    IconButton(onClick = { /* Bookmark or favorite */ }) {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPeach)
                .padding(innerPadding)
        ) {
            // Course progress card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "HTML Course Progress",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Row {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Points",
                                tint = PrimaryBlue
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "640 XP",
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = 0.8f,
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
                        Text(
                            text = "80% complete",
                            fontSize = 14.sp,
                            color = TextDark
                        )

                        Text(
                            text = "4/7 modules completed",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextDark
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Continue learning */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Continue Learning",
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

            // Tabs for modules and projects
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = BackgroundPeach,
                contentColor = PrimaryBlue
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Learning Modules") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Projects") }
                )
            }

            when (selectedTabIndex) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(htmlModules) { module ->
                            ModuleCard(
                                module = module,
                                onClick = { id ->
                                    navController.navigate("htmlLesson1/$id")
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                1 -> {
                    // Projects content
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(htmlProjects) { project ->
                            ProjectCard(project = project)
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModuleCard(
    module: HtmlModule,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = !module.isLocked) {
                onClick(module.id)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (module.isLocked) Color.LightGray.copy(alpha = 0.2f) else White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Module icon with status
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        when {
                            module.isLocked -> Color.Gray.copy(alpha = 0.2f)
                            module.isCompleted -> Color(0xFF58C27D).copy(alpha = 0.2f)
                            else -> PrimaryBlue.copy(alpha = 0.2f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when {
                        module.isLocked -> Icons.Default.Lock
                        module.isCompleted -> Icons.Default.CheckCircle
                        else -> Icons.Default.Code
                    },
                    contentDescription = null,
                    tint = when {
                        module.isLocked -> Color.Gray
                        module.isCompleted -> Color(0xFF58C27D)
                        else -> PrimaryBlue
                    },
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = module.title,
                    fontWeight = FontWeight.Bold,
                    color = if (module.isLocked) TextDark.copy(alpha = 0.5f) else TextDark
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = module.description,
                    fontSize = 12.sp,
                    color = if (module.isLocked) TextDark.copy(alpha = 0.3f) else TextDark.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = module.difficulty,
                        fontSize = 12.sp,
                        color = if (module.isLocked) TextDark.copy(alpha = 0.3f) else PrimaryBlue,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "•",
                        color = TextDark.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = module.duration,
                        fontSize = 12.sp,
                        color = if (module.isLocked) TextDark.copy(alpha = 0.3f) else TextDark.copy(alpha = 0.7f)
                    )
                }

                if (!module.isLocked) {
                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = module.progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = if (module.isCompleted) Color(0xFF58C27D) else PrimaryBlue,
                        trackColor = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: HtmlProject) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Open project */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = project.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextDark
                )

                if (project.isFeatured) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Featured",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = project.description,
                fontSize = 14.sp,
                color = TextDark.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = project.difficulty,
                    fontSize = 12.sp,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "•",
                    color = TextDark.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${project.estimatedHours} hrs",
                    fontSize = 12.sp,
                    color = TextDark.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HTMLScreenPreview() {
    HTMLScreen(navController = rememberNavController())
}