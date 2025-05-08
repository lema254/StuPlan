package com.example.stuplan.ui.theme.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun SplashScreen(onSplashComplete: () -> Unit = {}) {
    val backgroundColor = Color.Black
    val dotColor = Color.White
    val lineColor = Color.White.copy(alpha = 0.3f)
    val accentColor = Color.White

    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "networkAnimation")
    val animationProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "networkAnimationProgress"
    )

    // Progress indicator animation
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = true) {
        // Simulate loading progress
        repeat(100) {
            delay(20)
            progress = (it + 1) / 100f
        }
        delay(500) // Short delay after the progress completes
        onSplashComplete()
    }

    // Data structure for our network visualization
    val networkNodes = remember {
        List(120) {
            NetworkNode(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = if (Random.nextFloat() > 0.85f)
                    Random.nextFloat() * 3f + 2f
                else
                    Random.nextFloat() * 1.5f + 0.5f,
                connections = mutableListOf()
            )
        }
    }

    // Create connections between nodes
    remember {
        // Add connections between nodes
        networkNodes.forEachIndexed { index, node ->
            // Connect to 1-3 other nodes
            val connectionCount = Random.nextInt(1, 4)
            repeat(connectionCount) {
                val targetIndex = Random.nextInt(networkNodes.size)
                if (targetIndex != index &&
                    !node.connections.contains(targetIndex) &&
                    nodeDistance(node, networkNodes[targetIndex]) < 0.3f) {
                    node.connections.add(targetIndex)
                }
            }
        }
        networkNodes
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Network visualization
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val rotation = animationProgress.value * 360 * 0.2f // Rotate slowly
            val scale = 0.95f + sin(animationProgress.value * Math.PI.toFloat() * 2) * 0.05f
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw connections
            networkNodes.forEachIndexed { index, node ->
                val nodeX = centerX + (node.x * 2 - 1) * size.width * 0.4f * scale
                val nodeY = centerY + (node.y * 2 - 1) * size.height * 0.4f * scale

                // Rotate the node position
                val rotatedX = centerX + (nodeX - centerX) * cos(Math.toRadians(rotation.toDouble()).toFloat()) -
                        (nodeY - centerY) * sin(Math.toRadians(rotation.toDouble()).toFloat())
                val rotatedY = centerY + (nodeX - centerX) * sin(Math.toRadians(rotation.toDouble()).toFloat()) +
                        (nodeY - centerY) * cos(Math.toRadians(rotation.toDouble()).toFloat())

                // Draw lines to connections
                node.connections.forEach { targetIndex ->
                    val targetNode = networkNodes[targetIndex]
                    val targetX = centerX + (targetNode.x * 2 - 1) * size.width * 0.4f * scale
                    val targetY = centerY + (targetNode.y * 2 - 1) * size.height * 0.4f * scale

                    // Rotate the target position
                    val rotatedTargetX = centerX + (targetX - centerX) * cos(Math.toRadians(rotation.toDouble()).toFloat()) -
                            (targetY - centerY) * sin(Math.toRadians(rotation.toDouble()).toFloat())
                    val rotatedTargetY = centerY + (targetX - centerX) * sin(Math.toRadians(rotation.toDouble()).toFloat()) +
                            (targetY - centerY) * cos(Math.toRadians(rotation.toDouble()).toFloat())

                    drawLine(
                        color = lineColor,
                        start = Offset(rotatedX, rotatedY),
                        end = Offset(rotatedTargetX, rotatedTargetY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }

            // Draw nodes
            networkNodes.forEach { node ->
                val nodeX = centerX + (node.x * 2 - 1) * size.width * 0.4f * scale
                val nodeY = centerY + (node.y * 2 - 1) * size.height * 0.4f * scale

                // Rotate the node position
                val rotatedX = centerX + (nodeX - centerX) * cos(Math.toRadians(rotation.toDouble()).toFloat()) -
                        (nodeY - centerY) * sin(Math.toRadians(rotation.toDouble()).toFloat())
                val rotatedY = centerY + (nodeX - centerX) * sin(Math.toRadians(rotation.toDouble()).toFloat()) +
                        (nodeY - centerY) * cos(Math.toRadians(rotation.toDouble()).toFloat())

                drawCircle(
                    color = dotColor.copy(alpha = 0.6f + node.size / 10f),
                    radius = node.size.dp.toPx(),
                    center = Offset(rotatedX, rotatedY)
                )
            }
        }

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Build the",
                color = accentColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Future",
                color = accentColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Progress bar
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp * progress)
                        .background(accentColor)
                )
            }
        }

        // App name or logo could go here if desired
        /*
        Text(
            text = "StudyPlanner",
            color = accentColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        )
        */
    }
}

// Data class to represent a node in our network
data class NetworkNode(
    val x: Float, // Normalized position (0-1)
    val y: Float, // Normalized position (0-1)
    val size: Float, // Node size
    val connections: MutableList<Int> // Indices of connected nodes
)

// Utility function to calculate distance between nodes
private fun nodeDistance(node1: NetworkNode, node2: NetworkNode): Float {
    val dx = node1.x - node2.x
    val dy = node1.y - node2.y
    return sqrt(dx * dx + dy * dy)
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}