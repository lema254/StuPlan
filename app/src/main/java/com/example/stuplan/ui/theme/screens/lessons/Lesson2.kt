package com.example.stuplan.ui.theme.screens.lessons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.tooling.preview.Preview
import com.example.stuplan.ui.theme.screens.contentscreens.BackgroundPeach
import com.example.stuplan.ui.theme.screens.contentscreens.HtmlModule
import com.example.stuplan.ui.theme.screens.contentscreens.PrimaryBlue
import com.example.stuplan.ui.theme.screens.contentscreens.TextDark
import com.example.stuplan.ui.theme.screens.contentscreens.White

// New screen for Lesson 2: HTML5 Semantic Elements

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen2(
    navController: NavController,
    moduleId: Int
) {
    val module = sampleHtmlModules.firstOrNull { it.id == moduleId } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = module.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
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
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = module.description,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (moduleId) {
                2 -> {
                    // Lesson 2 Content: HTML5 Semantic Elements
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = BackgroundPeach)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "HTML5 Semantic Elements",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Semantic HTML5 elements give meaning to the web page structure, making it more accessible and easier to understand. These elements help both the browser and developers identify the content's role in the page.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = BackgroundPeach)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Key Points:",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryBlue
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("- The <header> element represents introductory content.", color = TextDark)
                            Text("- The <footer> element contains the footer information of the page.", color = TextDark)
                            Text("- The <article> element represents a self-contained piece of content.", color = TextDark)
                            Text("- The <section> element represents a section in the document.", color = TextDark)
                            Text("- The <nav> element represents navigation links.", color = TextDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Example HTML5 code snippet
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Text(
                            text = "<!-- Example HTML5 Semantic Elements -->\n" +
                                    "<header>\n" +
                                    "  <h1>Welcome to My Website</h1>\n" +
                                    "</header>\n" +
                                    "<nav>\n" +
                                    "  <ul>\n" +
                                    "    <li><a href=\"#home\">Home</a></li>\n" +
                                    "    <li><a href=\"#about\">About</a></li>\n" +
                                    "  </ul>\n" +
                                    "</nav>\n" +
                                    "<section>\n" +
                                    "  <h2>Introduction</h2>\n" +
                                    "  <p>This is an example of HTML5 semantic elements.</p>\n" +
                                    "</section>\n" +
                                    "<footer>\n" +
                                    "  <p>&copy; 2025 Example Website</p>\n" +
                                    "</footer>",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = TextDark
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Button to mark as learned
                    Button(
                        onClick = { /* mark complete or next */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Mark as Learned", color = White)
                    }
                }
            }
        }
    }
}

// Sample HTML Module Data
val sampleHtmlModules = listOf(
    HtmlModule(1, "HTML Fundamentals", "Learn the basic structure...", "Beginner", "2h", true, false, 1f),
    HtmlModule(2, "HTML5 Semantic Elements", "Understand how to structure your content with semantic HTML5 elements", "Beginner", "3 hours", true, false, 1f)
    // Add other modules as necessary...
)

