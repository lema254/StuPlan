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

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)


    private fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun logout(navController: NavController, context: Context) {
        mAuth.signOut()
        _currentUser.value = null
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(ROUTE_DASHBOARD) { inclusive = true }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        context: Context,
        navController: NavController
    ) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }

        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val user = mAuth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            val userId = user.uid
                            val userData = User(
                                name = name,
                                email = email,

                            )
                            saveUserToDatabase(userId, userData, context, navController)
                        } else {
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
        val regRef = FirebaseDatabase.getInstance().getReference("Users/$userId")

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
                    val userId = mAuth.currentUser?.uid ?: return@addOnCompleteListener
                    FirebaseDatabase.getInstance()
                        .getReference("Users/$userId")
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val userData = snapshot.getValue(User::class.java)
                            userData?.let {
                                setCurrentUser(it)
                                Toast.makeText(context, "Logged in as ${it.name}", Toast.LENGTH_LONG).show()
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
