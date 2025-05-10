import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import coil.compose.rememberImagePainter
import java.io.File
import java.io.FileOutputStream



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    // State variables for form fields
    var username by remember { mutableStateOf(userProfile?.username ?: "") }
    var displayName by remember { mutableStateOf(userProfile?.displayName ?: "") }
    var bio by remember { mutableStateOf(userProfile?.bio ?: "") }
    var website by remember { mutableStateOf(userProfile?.website ?: "") }
    var phoneNumber by remember { mutableStateOf(userProfile?.phoneNumber ?: "") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Update local state when profile data is loaded
    LaunchedEffect(userProfile) {
        userProfile?.let {
            username = it.username
            displayName = it.displayName
            bio = it.bio
            website = it.website
            phoneNumber = it.phoneNumber
        }
    }

    // Load user profile on screen launch
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it

            try {
                // Convert Uri to File for upload
                val inputStream = context.contentResolver.openInputStream(uri)
                val tempFile = File.createTempFile("profile_", ".jpg", context.cacheDir)
                val outputStream = FileOutputStream(tempFile)

                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                // Upload the profile image immediately
                viewModel.uploadProfileImage(tempFile)
            } catch (e: Exception) {
                // Handle any errors that might occur during file processing
                viewModel._errorMessage.value = "Failed to process image: ${e.message}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            userProfile?.let {
                                val updatedUser = it.copy(
                                    username = username,
                                    displayName = displayName,
                                    bio = bio,
                                    website = website,
                                    phoneNumber = phoneNumber
                                )
                                viewModel.updateProfile(updatedUser)
                                onNavigateBack()
                            }
                        },
                        enabled = !isLoading
                    ) {
                        Text("Save", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Profile picture section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box {
                            // Profile image
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray)
                                    .border(1.dp, Color.Gray, CircleShape)
                                    .clickable { imagePickerLauncher.launch("image/*") }
                            ) {
                                val imageUrl = selectedImageUri?.toString() ?: userProfile?.profileImageUrl

                                if (!imageUrl.isNullOrEmpty()) {
                                    Image(
                                        painter = rememberImagePainter(imageUrl),
                                        contentDescription = "Profile Picture",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Default Profile",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center),
                                        tint = Color.Gray
                                    )
                                }
                            }

                            // Camera icon overlay
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = "Change Photo",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(4.dp),
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Change Profile Photo",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable { imagePickerLauncher.launch("image/*") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Form fields
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text("Name") },
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = website,
                    onValueChange = { website = it },
                    label = { Text("Website") },
                    leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    )
                )

                // Display error message if any
                errorMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}