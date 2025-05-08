package com.example.stuplan.data


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuplan.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Status updates for profile modifications
sealed class ProfileUpdateStatus {
    object Idle : ProfileUpdateStatus()
    object Loading : ProfileUpdateStatus()
    object Success : ProfileUpdateStatus()
    data class Error(val message: String) : ProfileUpdateStatus()
}

// User profile data model
data class UserProfile(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val bio: String = "",
    val academicLevel: String = "Undergraduate Student",
    val completedTasks: Int = 0,
    val studySessions: Int = 0,
    val streak: Int = 0
)

class ProfileViewModel : ViewModel() {

    // State for the user profile
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    // State for tracking profile update status
    private val _profileUpdateStatus = MutableStateFlow<ProfileUpdateStatus>(ProfileUpdateStatus.Idle)
    val profileUpdateStatus: StateFlow<ProfileUpdateStatus> = _profileUpdateStatus.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Load the user profile data
    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                // In a real app, this would fetch from a repository or service
                // Mock data for demonstration
                _userProfile.value = UserProfile(
                    userId = "user123",
                    displayName = "Alex Johnson",
                    email = "alex.j@example.com",
                    photoUrl = "avatar_1",
                    bio = "Computer Science student passionate about mobile development and AI.",
                    academicLevel = "Undergraduate Student",
                    completedTasks = 42,
                    studySessions = 28,
                    streak = 7
                )
            } catch (e: Exception) {
                // Handle errors
                _profileUpdateStatus.value = ProfileUpdateStatus.Error("Failed to load profile: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Update the user profile information
    fun updateProfile(displayName: String, bio: String, academicLevel: String) {
        viewModelScope.launch {
            _profileUpdateStatus.value = ProfileUpdateStatus.Loading
            _isLoading.value = true

            try {
                // In a real app, this would update to a backend or local database
                _userProfile.value = _userProfile.value?.copy(
                    displayName = displayName,
                    bio = bio,
                    academicLevel = academicLevel
                )

                _profileUpdateStatus.value = ProfileUpdateStatus.Success
            } catch (e: Exception) {
                _profileUpdateStatus.value = ProfileUpdateStatus.Error("Failed to update profile: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Update just the avatar
    fun updateProfileAvatar(avatarUrl: String) {
        viewModelScope.launch {
            _profileUpdateStatus.value = ProfileUpdateStatus.Loading
            _isLoading.value = true

            try {
                // In a real app, this would update to a backend or local database
                _userProfile.value = _userProfile.value?.copy(
                    photoUrl = avatarUrl
                )

                _profileUpdateStatus.value = ProfileUpdateStatus.Success
            } catch (e: Exception) {
                _profileUpdateStatus.value = ProfileUpdateStatus.Error("Failed to update avatar: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Reset update status after handling
    fun clearUpdateStatus() {
        _profileUpdateStatus.value = ProfileUpdateStatus.Idle
    }
}



//
//import android.net.Uri
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.stuplan.models.NotificationSettings
//import com.example.stuplan.models.UserProfile
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.UserProfileChangeRequest
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//
//class ProfileViewModel : ViewModel() {
//
//    // Firebase instances
//    private val auth = FirebaseAuth.getInstance()
//    private val firestore = FirebaseFirestore.getInstance()
//
//    // User collection reference
//    private val usersCollection = firestore.collection("users")
//
//    // Tag for logging
//    private val TAG = "ProfileViewModel"
//
//    // StateFlow to expose user profile data to the UI
//    private val _userProfile = MutableStateFlow<UserProfile?>(null)
//    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()
//
//    // Status for profile operations
//    private val _profileUpdateStatus = MutableStateFlow<ProfileUpdateStatus>(ProfileUpdateStatus.Idle)
//    val profileUpdateStatus: StateFlow<ProfileUpdateStatus> = _profileUpdateStatus.asStateFlow()
//
//    // Loading state
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
//
//    init {
//        // Load user profile when ViewModel is created
//        loadUserProfile()
//    }
//
//    /**
//     * Loads the current user profile from Firestore
//     */
//    fun loadUserProfile() {
//        viewModelScope.launch {
//            _isLoading.value = true
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Fetch user profile from Firestore
//                    val userDocument = usersCollection.document(currentUser.uid).get().await()
//
//                    if (userDocument.exists()) {
//                        // Convert document to UserProfile
//                        val profile = userDocument.toObject(UserProfile::class.java)
//                        _userProfile.value = profile
//                    } else {
//                        // Create a new profile if it doesn't exist
//                        val newProfile = createDefaultProfile(currentUser)
//                        _userProfile.value = newProfile
//
//                        // Save the new profile to Firestore
//                        usersCollection.document(currentUser.uid).set(newProfile).await()
//                    }
//                } else {
//                    // No authenticated user
//                    _userProfile.value = null
//                }
//            } catch (e: Exception) {
//                // Handle error
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Unknown error loading profile")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Creates a default profile for a new user
//     */
//    private fun createDefaultProfile(user: FirebaseUser): UserProfile {
//        return UserProfile(
//            userId = user.uid,
//            displayName = user.displayName ?: "Student",
//            email = user.email ?: "",
//            photoUrl = user.photoUrl?.toString(),
//            joinDate = System.currentTimeMillis(),
//            academicLevel = "Student" // Default value
//        )
//    }
//
//    /**
//     * Updates the user profile information
//     */
//    fun updateProfile(
//        displayName: String,
//        bio: String,
//        academicLevel: String,
//        interests: List<String> = _userProfile.value?.interests ?: emptyList(),
//        preferredSubjects: List<String> = _userProfile.value?.preferredSubjects ?: emptyList()
//    ) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _profileUpdateStatus.value = ProfileUpdateStatus.Loading
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update Firebase Auth display name
//                    val profileUpdates = UserProfileChangeRequest.Builder()
//                        .setDisplayName(displayName)
//                        .build()
//
//                    currentUser.updateProfile(profileUpdates).await()
//
//                    // Get current profile and create updated version
//                    val currentProfile = _userProfile.value ?: createDefaultProfile(currentUser)
//                    val updatedProfile = currentProfile.copy(
//                        displayName = displayName,
//                        bio = bio,
//                        academicLevel = academicLevel,
//                        interests = interests,
//                        preferredSubjects = preferredSubjects
//                    )
//
//                    // Update in Firestore
//                    usersCollection.document(currentUser.uid).set(updatedProfile).await()
//
//                    // Update local state
//                    _userProfile.value = updatedProfile
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Unknown error updating profile")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates the user profile picture with an avatar type identifier
//     * This approach uses a string identifier instead of actual image storage
//     */
//    fun updateProfileAvatar(avatarType: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _profileUpdateStatus.value = ProfileUpdateStatus.Loading
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Store avatar identifier in Firestore
//                    // This could be "avatar1", "avatar2", etc. or any identification system you choose
//                    usersCollection.document(currentUser.uid).update("photoUrl", avatarType).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value ?: createDefaultProfile(currentUser)
//                    val updatedProfile = currentProfile.copy(photoUrl = avatarType)
//
//                    _userProfile.value = updatedProfile
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//
//                    Log.d(TAG, "Updated user avatar to: $avatarType")
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error updating avatar: ${e.message}", e)
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update profile avatar")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates the user profile with an external image URL
//     * Use this if you're storing images elsewhere or using a service like Gravatar
//     */
//    fun updateProfilePictureUrl(imageUrl: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _profileUpdateStatus.value = ProfileUpdateStatus.Loading
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update Firebase Auth profile
//                    val profileUpdates = UserProfileChangeRequest.Builder()
//                        .setPhotoUri(Uri.parse(imageUrl))
//                        .build()
//
//                    currentUser.updateProfile(profileUpdates).await()
//
//                    // Update Firestore document
//                    usersCollection.document(currentUser.uid).update("photoUrl", imageUrl).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value ?: createDefaultProfile(currentUser)
//                    val updatedProfile = currentProfile.copy(photoUrl = imageUrl)
//
//                    _userProfile.value = updatedProfile
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//
//                    Log.d(TAG, "Updated profile picture URL successfully")
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error updating profile picture URL: ${e.message}", e)
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update profile picture")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates user interests
//     */
//    fun updateInterests(interests: List<String>) {
//        viewModelScope.launch {
//            _isLoading.value = true
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update in Firestore
//                    usersCollection.document(currentUser.uid).update("interests", interests).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value
//                    if (currentProfile != null) {
//                        _userProfile.value = currentProfile.copy(interests = interests)
//                    }
//
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update interests")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates preferred subjects
//     */
//    fun updatePreferredSubjects(subjects: List<String>) {
//        viewModelScope.launch {
//            _isLoading.value = true
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update in Firestore
//                    usersCollection.document(currentUser.uid).update("preferredSubjects", subjects).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value
//                    if (currentProfile != null) {
//                        _userProfile.value = currentProfile.copy(preferredSubjects = subjects)
//                    }
//
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update subjects")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates notification settings
//     */
//    fun updateNotificationSettings(settings: NotificationSettings) {
//        viewModelScope.launch {
//            _isLoading.value = true
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update in Firestore
//                    usersCollection.document(currentUser.uid).update("notificationSettings", settings).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value
//                    if (currentProfile != null) {
//                        _userProfile.value = currentProfile.copy(notificationSettings = settings)
//                    }
//
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update notification settings")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates theme preference
//     */
//    fun updateThemePreference(theme: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update in Firestore
//                    usersCollection.document(currentUser.uid).update("themePreference", theme).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value
//                    if (currentProfile != null) {
//                        _userProfile.value = currentProfile.copy(themePreference = theme)
//                    }
//
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Success
//                } else {
//                    _profileUpdateStatus.value = ProfileUpdateStatus.Error("User not authenticated")
//                }
//            } catch (e: Exception) {
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update theme preference")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    /**
//     * Updates learning progress
//     */
//    fun updateLearningProgress(completedSections: Int, totalSections: Int) {
//        viewModelScope.launch {
//            try {
//                val currentUser = auth.currentUser
//
//                if (currentUser != null) {
//                    // Update in Firestore
//                    usersCollection.document(currentUser.uid)
//                        .update(
//                            mapOf(
//                                "completedSections" to completedSections,
//                                "totalSections" to totalSections
//                            )
//                        ).await()
//
//                    // Update local state
//                    val currentProfile = _userProfile.value
//                    if (currentProfile != null) {
//                        _userProfile.value = currentProfile.copy(
//                            completedSections = completedSections,
//                            totalSections = totalSections
//                        )
//                    }
//                }
//            } catch (e: Exception) {
//                _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to update progress")
//            }
//        }
//    }
//
//    /**
//     * Signs out the current user
//     */
//    suspend fun signOut() {
//        try {
//            auth.signOut()
//            _userProfile.value = null
//        } catch (e: Exception) {
//            _profileUpdateStatus.value = ProfileUpdateStatus.Error(e.message ?: "Failed to sign out")
//        }
//    }
//
//    /**
//     * Clear any error status
//     */
//    fun clearUpdateStatus() {
//        _profileUpdateStatus.value = ProfileUpdateStatus.Idle
//    }
//}
//
///**
// * Sealed class to represent the status of profile update operations
// */
//sealed class ProfileUpdateStatus {
//    object Idle : ProfileUpdateStatus()
//    object Loading : ProfileUpdateStatus()
//    object Success : ProfileUpdateStatus()
//    data class Error(val message: String) : ProfileUpdateStatus()
//}
