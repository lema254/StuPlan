package com.example.stuplan.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stuplan.data.ProfileUpdateStatus
import com.example.stuplan.data.ProfileViewModel
import com.example.stuplan.Avatar.AvatarSelector
import com.example.stuplan.Avatar.UserAvatar
import com.example.stuplan.ui.theme.screens.dashboard.BackgroundPeach
import com.example.stuplan.ui.theme.screens.dashboard.TextDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    val updateStatus by profileViewModel.profileUpdateStatus.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Form state
    var displayName by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var academicLevel by remember { mutableStateOf("") }
    var selectedAvatar by remember { mutableStateOf<String?>(null) }

    // Available academic levels
    val academicLevels = listOf(
        "High School Student",
        "Undergraduate Student",
        "Graduate Student",
        "PhD Student",
        "Lecturer",
        "Professor",
        "Independent Learner"
    )

    // Initialize form with user data when available
    LaunchedEffect(userProfile) {
        userProfile?.let { profile ->
            displayName = profile.displayName
            bio = profile.bio
            academicLevel = profile.academicLevel
            selectedAvatar = profile.photoUrl
        }
    }

    // Show snackbar on updates
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(updateStatus) {
        when (updateStatus) {
            is ProfileUpdateStatus.Success -> {
                snackbarHostState.showSnackbar("Profile updated successfully")
                profileViewModel.clearUpdateStatus()
            }

            is ProfileUpdateStatus.Error -> {
                snackbarHostState.showSnackbar(
                    (updateStatus as ProfileUpdateStatus.Error).message,
                    duration = SnackbarDuration.Long
                )
                profileViewModel.clearUpdateStatus()
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
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
                    navigationIconContentColor = TextDark,
                    titleContentColor = TextDark
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (userProfile == null) {
            // Show loading or error state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Section
                userProfile?.let { profile ->
                    UserAvatar(
                        displayName = profile.displayName,
                        photoUrl = selectedAvatar,
                        userId = profile.userId,
                        size = 100,
                        onClick = null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Select Avatar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AvatarSelector(
                        selectedAvatar = selectedAvatar,
                        onAvatarSelected = { avatarType ->
                            selectedAvatar = avatarType
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Display Name Field
                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text("Display Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Name"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Academic Level Dropdown
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { }
                ) {
                    OutlinedTextField(
                        value = academicLevel,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Academic Level") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Academic Level"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = { }
                    ) {
                        academicLevels.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = {
                                    academicLevel = level
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bio Field
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Bio"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Update Button
                Button(
                    onClick = {
                        scope.launch {
                            // Update profile info
                            profileViewModel.updateProfile(
                                displayName = displayName,
                                bio = bio,
                                academicLevel = academicLevel
                            )

                            // Update avatar separately if changed
                            selectedAvatar?.let { avatar ->
                                if (avatar != userProfile?.photoUrl) {
                                    profileViewModel.updateProfileAvatar(avatar)
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Save Changes")

                    }
                }
            }
        }
    }


}
    @Preview(showBackground = true)
    @Composable
    fun ProfileScreenPreview() {
        EditProfileScreen(rememberNavController())
}