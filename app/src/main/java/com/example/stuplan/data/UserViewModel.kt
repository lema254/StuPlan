package com.example.lema.data

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.stuplan.models.UserModel
import com.example.lema.util.validateEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class UserViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // State holders
    val user = mutableStateOf<UserModel?>(null)
    val isLoading = mutableStateOf(false)

    init {
        // Load user data when the ViewModel is initialized
        loadUserData()
    }

    /**
     * Load the current user's profile data from Firestore
     */
    fun loadUserData() {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // User not logged in
            user.value = null
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true

                val documentSnapshot = withContext(Dispatchers.IO) {
                    firestore.collection("users")
                        .document(currentUser.uid)
                        .get()
                        .await()
                }

                if (documentSnapshot.exists()) {
                    // User profile exists
                    val userData = documentSnapshot.toObject(UserModel::class.java)
                    userData?.userId = currentUser.uid
                    user.value = userData
                } else {
                    // No profile created yet
                    user.value = null
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading user data: ${e.message}")
                user.value = null
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Create a new user profile
     */
    fun createUserProfile(
        context: Context,
        navController: NavController,
        name: String,
        email: String,
        bio: String = "",
        phoneNumber: String = "",
        university: String = "",
        imageUri: Uri? = null,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onError("User not authenticated")
            return
        }

        if (name.trim().isEmpty()) {
            onError("Name is required")
            return
        }

        if (email.trim().isEmpty() || !validateEmail(email)) {
            onError("Valid email is required")
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true

                // Upload image if provided
                val profileImageUrl = imageUri?.let { uploadImageToStorage(it) } ?: ""

                // Create user model
                val userModel = UserModel(
                    userId = currentUser.uid,
                    name = name,
                    email = email,
                    bio = bio,
                    phoneNumber = phoneNumber,
                    university = university,
                    profileImage = profileImageUrl
                )

                // Save to Firestore
                withContext(Dispatchers.IO) {
                    firestore.collection("users")
                        .document(currentUser.uid)
                        .set(userModel)
                        .await()
                }

                // Update local state
                user.value = userModel

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Profile created successfully", Toast.LENGTH_SHORT).show()
                    onSuccess()
                    // Navigate back to profile view
                    navController.navigate("view_profile") {
                        popUpTo("edit_profile") { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error creating profile: ${e.message}")
                withContext(Dispatchers.Main) {
                    val errorMsg = "Failed to create profile: ${e.localizedMessage}"
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    onError(errorMsg)
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Update an existing user profile
     */
    fun updateProfile(
        context: Context,
        navController: NavController,
        name: String,
        email: String,
        bio: String = "",
        phoneNumber: String = "",
        university: String = "",
        imageUri: Uri? = null,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val currentUser = auth.currentUser
        val currentUserData = user.value

        if (currentUser == null || currentUserData == null) {
            onError("User not authenticated or profile not loaded")
            return
        }

        if (name.trim().isEmpty()) {
            onError("Name is required")
            return
        }

        if (email.trim().isEmpty() || !validateEmail(email)) {
            onError("Valid email is required")
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true

                // Upload new image if provided
                val profileImageUrl = when {
                    imageUri != null -> uploadImageToStorage(imageUri)
                    currentUserData.profileImage?.isNotEmpty() == true -> currentUserData.profileImage
                    else -> ""
                }

                // Create updated user model
                val updatedUserModel = UserModel(
                    userId = currentUser.uid,
                    name = name,
                    email = email,
                    bio = bio,
                    phoneNumber = phoneNumber,
                    university = university,
                    profileImage = profileImageUrl
                )

                // Update in Firestore
                withContext(Dispatchers.IO) {
                    firestore.collection("users")
                        .document(currentUser.uid)
                        .set(updatedUserModel)
                        .await()
                }

                // Update local state
                user.value = updatedUserModel

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    onSuccess()
                    // Navigate back to profile view
                    navController.navigate("view_profile") {
                        popUpTo("edit_profile") { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error updating profile: ${e.message}")
                withContext(Dispatchers.Main) {
                    val errorMsg = "Failed to update profile: ${e.localizedMessage}"
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    onError(errorMsg)
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Delete the user's profile
     */
    fun deleteProfile(
        context: Context,
        navController: NavController,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onError("User not authenticated")
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true

                // Delete profile image from storage if exists
                user.value?.profileImage?.let { imageUrl ->
                    if (imageUrl.isNotEmpty()) {
                        try {
                            // Extract the path from the URL to delete from storage
                            val storageRef = storage.getReferenceFromUrl(imageUrl)
                            storageRef.delete().await()
                        } catch (e: Exception) {
                            Log.e("UserViewModel", "Error deleting profile image: ${e.message}")
                            // Continue with profile deletion even if image deletion fails
                        }
                    }
                }

                // Delete user document from Firestore
                withContext(Dispatchers.IO) {
                    firestore.collection("users")
                        .document(currentUser.uid)
                        .delete()
                        .await()
                }

                // Clear local state
                user.value = null

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                    onSuccess()
                    // Navigate to appropriate screen (maybe home or login)
                    navController.navigate("home") {
                        popUpTo("view_profile") { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error deleting profile: ${e.message}")
                withContext(Dispatchers.Main) {
                    val errorMsg = "Failed to delete profile: ${e.localizedMessage}"
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    onError(errorMsg)
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Upload an image to Firebase Storage
     */
    private suspend fun uploadImageToStorage(imageUri: Uri): String {
        return withContext(Dispatchers.IO) {
            try {
                val filename = "profile_${UUID.randomUUID()}"
                val storageRef = storage.reference.child("profile_images/$filename")

                // Upload file
                val uploadTask = storageRef.putFile(imageUri).await()

                // Get download URL
                storageRef.downloadUrl.await().toString()
            } catch (e: Exception) {
                Log.e("UserViewModel", "Image upload failed: ${e.message}")
                throw e
            }
        }
    }

    /**
     * Log out the current user
     */
    fun logOut(
        context: Context,
        navController: NavController
    ) {
        auth.signOut()
        user.value = null
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
        navController.navigate("login") {
            popUpTo("view_profile") { inclusive = true }
        }
    }

    /**
     * Update profile image only
     */
    fun updateProfileImage(
        context: Context,
        imageUri: Uri,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val currentUser = auth.currentUser
        val currentUserData = user.value

        if (currentUser == null || currentUserData == null) {
            onError("User not authenticated or profile not loaded")
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true

                // Upload new image
                val profileImageUrl = uploadImageToStorage(imageUri)

                // Update only the profile image field
                withContext(Dispatchers.IO) {
                    firestore.collection("users")
                        .document(currentUser.uid)
                        .update("profileImage", profileImageUrl)
                        .await()
                }

                // Update local state
                user.value = currentUserData.copy(profileImage = profileImageUrl)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Profile image updated", Toast.LENGTH_SHORT).show()
                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error updating profile image: ${e.message}")
                withContext(Dispatchers.Main) {
                    val errorMsg = "Failed to update profile image: ${e.localizedMessage}"
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    onError(errorMsg)
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Check if user is logged in
     */
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}