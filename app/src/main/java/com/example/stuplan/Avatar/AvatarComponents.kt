package com.example.stuplan.Avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stuplan.utils.AvatarUtils

/**
 * A composable that displays a user's avatar without relying on Firebase Storage
 *
 * @param displayName The user's display name
 * @param photoUrl The user's photo URL or avatar type identifier
 * @param userId The user's unique ID to generate consistent colors
 * @param size The size of the avatar in dp
 * @param onClick Optional click handler for the avatar
 */
@Composable
fun UserAvatar(
    displayName: String,
    photoUrl: String?,
    userId: String,
    size: Int = 100,
    onClick: (() -> Unit)? = null
) {
    val avatarModifier = Modifier
        .size(size.dp)
        .clip(CircleShape)
        .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
        .then(
            if (onClick != null) {
                Modifier.clickable { onClick() }
            } else {
                Modifier
            }
        )

    Box(
        modifier = avatarModifier.background(AvatarUtils.getColorForUser(userId)),
        contentAlignment = Alignment.Center
    ) {
        if (AvatarUtils.isAvatarType(photoUrl)) {
            // If it's a predefined avatar type, show appropriate indicator
            // You could implement custom avatar graphics for each type
            // For now, just showing the first letter of the avatar type
            Text(
                text = photoUrl?.firstOrNull()?.uppercase() ?: "?",
                fontSize = (size / 2.3).sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        } else {
            // Show user's initials
            Text(
                text = AvatarUtils.getInitials(displayName),
                fontSize = (size / 2.5).sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

/**
 * A composable that displays a list of avatar options for the user to choose from
 *
 * @param onAvatarSelected Callback when an avatar is selected
 */
@Composable
fun AvatarSelector(
    selectedAvatar: String?,
    onAvatarSelected: (String) -> Unit
) {
    val avatarTypes = listOf("default", "student", "graduate", "professor", "scientist",
        "artist", "musician", "athlete", "programmer", "designer")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        avatarTypes.take(5).forEach { avatarType ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        if (selectedAvatar == avatarType)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                    .border(
                        width = if (selectedAvatar == avatarType) 2.dp else 1.dp,
                        color = if (selectedAvatar == avatarType)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onAvatarSelected(avatarType) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = avatarType.first().uppercase(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedAvatar == avatarType)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    Spacer(modifier = Modifier
        .height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        avatarTypes.takeLast(5).forEach { avatarType ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        if (selectedAvatar == avatarType)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                    .border(
                        width = if (selectedAvatar == avatarType) 2.dp else 1.dp,
                        color = if (selectedAvatar == avatarType)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onAvatarSelected(avatarType) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = avatarType.first().uppercase(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedAvatar == avatarType)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}