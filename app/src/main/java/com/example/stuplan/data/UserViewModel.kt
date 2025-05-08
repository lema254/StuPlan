package com.example.stuplan.data



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val bio: String = "",
    val photoUrl: String = "",
    val academicLevel: String = "Intermediate"
)

class UserViewModel : ViewModel() {
    // User data state
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Error handling
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    /**
     * Register a new user
     */
    fun register(name: String, email: String, bio: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // In a real app, this would make an API call to register the user
                // For now, we'll simulate success and create a local user object

                // Simulate network delay
                kotlinx.coroutines.delay(1000)

                // Create user object
                val newUser = User(
                    id = generateRandomId(),
                    name = name,
                    email = email,
                    bio = bio,
                    academicLevel = "Beginner"
                )

                // Set the user in the ViewModel
                _user.value = newUser

                // Clear any previous errors
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Registration failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Update user profile information
     */
    fun updateProfile(name: String, email: String, bio: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Simulate network delay
                kotlinx.coroutines.delay(1000)

                // Update user object with new information
                _user.value = _user.value?.copy(
                    name = name,
                    email = email,
                    bio = bio
                )

                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Profile update failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Login with email and password
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // In a real app, this would validate credentials with a backend service
                // For now, we'll simulate success and create a mock user

                // Simulate network delay
                kotlinx.coroutines.delay(1000)

                // Create mock user object
                val loggedInUser = User(
                    id = generateRandomId(),
                    name = "Alex Johnson", // Mock name
                    email = email,
                    bio = "Passionate programmer learning mobile development with Kotlin and Android.",
                    academicLevel = "Intermediate"
                )

                // Set the user in the ViewModel
                _user.value = loggedInUser

                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Login failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Logout current user
     */
    fun logout() {
        viewModelScope.launch {
            _user.value = null
        }
    }

    /**
     * Check if a user is currently logged in
     */
    fun isLoggedIn(): Boolean {
        return _user.value != null
    }

    /**
     * Helper function to generate a random ID
     */
    private fun generateRandomId(): String {
        return java.util.UUID.randomUUID().toString()
    }
}