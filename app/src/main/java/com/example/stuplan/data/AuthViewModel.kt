package com.example.stuplan.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.stuplan.models.User
import com.example.stuplan.navigation.ROUTE_DASHBOARD
import com.example.stuplan.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Loading / error
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    // --- NEW: current user state ---
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    /** Call this after a successful login or registration to keep track of the user in-memory */
    private fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    /** Clears the current user (e.g. when logging out) */
    fun logout(navController: NavController, context: Context) {
        mAuth.signOut()
        _currentUser.value = null
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(ROUTE_DASHBOARD) { inclusive = true }
        }
    }

    fun register(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        context: Context,
        navController: NavController
    ) {
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }
        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // Get the current user from Firebase Authentication
                    val user = mAuth.currentUser

                    // Update the user's profile with their first name and last name
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName("$firstname $lastname")
                        .build()

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            // Successfully updated the user's display name
                            val userId = user?.uid ?: ""
                            val userData = User(
                                firstname = firstname,
                                lastname = lastname,
                                email = email,
                                password = password,
                                userId = userId
                            )
                            // Save user data to the database
                            saveUserToDatabase(userId, userData, context, navController)
                        } else {
                            // Handle profile update failure
                            Toast.makeText(context, "Profile update failed: ${updateTask.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUserToDatabase(
        userId: String,
        userData: User,
        context: Context,
        navController: NavController
    ) {
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")

        regRef.setValue(userData)
            .addOnCompleteListener { dbTask ->
                if (dbTask.isSuccessful) {
                    Toast.makeText(context, "User Successfully Registered", Toast.LENGTH_LONG).show()
                    setCurrentUser(userData)
                    navController.navigate(ROUTE_DASHBOARD) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                } else {
                    _errorMessage.value = dbTask.exception?.message
                    Toast.makeText(context, "Database error: ${dbTask.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }


    fun login(
        email: String,
        password: String,
        navController: NavController,
        context: Context
    ) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Email and password required", Toast.LENGTH_LONG).show()
            return
        }
        _isLoading.value = true

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // Fetch the user data from Realtime Database
                    val userId = mAuth.currentUser?.uid ?: return@addOnCompleteListener
                    FirebaseDatabase.getInstance()
                        .getReference("Users/$userId")
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val userData = snapshot.getValue(User::class.java)
                            userData?.let {
                                setCurrentUser(it)
                                Toast.makeText(context, "Logged in as ${it.firstname}", Toast.LENGTH_LONG).show()
                                navController.navigate(ROUTE_DASHBOARD) {
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            }
                        }
                        .addOnFailureListener { e ->
                            _errorMessage.value = e.message
                            Toast.makeText(context, "Fetch user failed: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
