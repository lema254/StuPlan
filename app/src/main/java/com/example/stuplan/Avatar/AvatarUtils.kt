package com.example.stuplan.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.absoluteValue
import kotlin.random.Random

/**
 * Utility class for generating and managing user avatars without using Firebase Storage
 */
object AvatarUtils {

    // List of predefined avatar types
    private val AVATAR_TYPES = listOf(
        "default", "student", "graduate", "professor", "scientist",
        "artist", "musician", "athlete", "programmer", "designer"
    )

    // List of avatar background colors
    private val AVATAR_COLORS = listOf(
        Color(0xFF6200EA), // Deep Purple
        Color(0xFF0091EA), // Light Blue
        Color(0xFF00B0FF), // Cyan
        Color(0xFF00C853), // Green
        Color(0xFFFFD600), // Yellow
        Color(0xFFFF6D00), // Orange
        Color(0xFFDD2C00), // Deep Orange
        Color(0xFFD50000), // Red
        Color(0xFFC51162), // Pink
        Color(0xFF304FFE)  // Indigo
    )

    /**
     * Get a random avatar type
     */
    fun getRandomAvatarType(): String {
        return AVATAR_TYPES[Random.nextInt(AVATAR_TYPES.size)]
    }

    /**
     * Get color for an avatar based on user ID or name
     */
    fun getColorForUser(userId: String): Color {
        // Generate a consistent color based on user ID
        val index = userId.hashCode().
        absoluteValue % AVATAR_COLORS.size
        return AVATAR_COLORS[index]
    }

    /**
     * Get user's initials from display name
     */
    fun getInitials(displayName: String): String {
        return if (displayName.isBlank()) {
            "?"
        } else {
            val parts = displayName.trim().split("\\s+".toRegex())
            when {
                parts.isEmpty() -> "?"
                parts.size == 1 -> parts[0].take(1).uppercase()
                else -> "${parts[0].take(1)}${parts.last().take(1)}".uppercase()
            }
        }
    }

    /**
     * Get a Gravatar URL for a user's email
     * Uses the MD5 hash of the user's email to generate a Gravatar URL
     */
    fun getGravatarUrl(email: String, size: Int = 200): String {
        val sanitizedEmail = email.trim().lowercase()
        val hash = java.security.MessageDigest.getInstance("MD5")
            .digest(sanitizedEmail.toByteArray())
            .joinToString("") { "%02x".format(it) }

        return "https://www.gravatar.com/avatar/$hash?s=$size&d=identicon"
    }

    /**
     * Get a UI-Avatar URL for a user's name
     * This service generates avatar images based on text input
     */
    fun getUIAvatarUrl(name: String, size: Int = 200): String {
        val encodedName = java.net.URLEncoder.encode(name, "UTF-8")
        return "https://ui-avatars.com/api/?name=$encodedName&size=$size&background=random"
    }

    /**
     * Determine if a photoUrl is a special avatar type rather than a URL
     */
    fun isAvatarType(photoUrl: String?): Boolean {
        return photoUrl != null && !photoUrl.startsWith("http") && AVATAR_TYPES.contains(photoUrl)
    }
}