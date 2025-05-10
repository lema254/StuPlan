
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.stuplan.ui.theme.screens.contentscreens.BackgroundPeach
import com.example.stuplan.ui.theme.screens.contentscreens.HtmlModule
import com.example.stuplan.ui.theme.screens.contentscreens.PrimaryBlue
import com.example.stuplan.ui.theme.screens.contentscreens.TextDark
import com.example.stuplan.ui.theme.screens.contentscreens.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen1(
    navController: NavController,
    moduleId: Int
) {
    // Fetch module data by ID (for now, pass in or look up from a static list)
    val module = sampleHtmlModules.firstOrNull { it.id == moduleId }
        ?: return // or show an error screen

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
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Show a code snippet example
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Text(
                    text = "<!-- Example HTML snippet for ${module.title} -->\n${getHtmlExample(module.id)}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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

// Helper functions and sample data accessible to both screens:

// In a shared file (e.g., SampleData.kt):
val sampleHtmlModules = listOf(
    HtmlModule(1, "HTML Fundamentals", "Learn the basic structure...", "Beginner", "2h", true, false, 1f),
    // ... other modules ...
)

fun getHtmlExample(id: Int): String {
    return when (id) {
        1 -> "<!DOCTYPE html>\n<html>\n  <head><title>Example</title></head>\n  <body>\n    <h1>Hello World</h1>\n  </body>\n</html>"
        2 -> "<section>\n  <header>..."
        // ... add further examples ...
        else -> ""
    }
}
