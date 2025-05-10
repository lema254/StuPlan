package com.example.stuplan.repsitory

import com.example.stuplan.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val usersCollection = firestore.collection("users")

    // Get current logged in user ID
    fun getCurrentUser(): String? = auth.currentUser?.uid

    // Get user profile by ID
    suspend fun getUserProfile(userId: String): Flow<Result<User>> = flow {
        try {
            val userDoc = usersCollection.document(userId).get().await()
            val user = userDoc.toObject(User::class.java)
                ?: User(
                    uid = userId,
                    name = "",
                    email = "",
                    profileImageUrl = null,
                    createdAt = 0L
                )
            emit(Result.success(user))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    // Create or update user profile
    suspend fun updateUserProfile(user: User): Flow<Result<Boolean>> = flow {
        try {
            val userId = user.uid.ifEmpty { auth.currentUser?.uid ?: throw IllegalStateException("User not logged in") }
            usersCollection.document(userId).set(user).await()
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    // Upload profile image to Imgur
    suspend fun uploadImageToImgur(imageFile: File): Flow<Result<String>> = flow {
        try {
            val clientId = "YOUR_IMGUR_CLIENT_ID"
            val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.name, requestBody)
                .build()

            val request = Request.Builder()
                .url("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID $clientId")
                .post(multipartBody)
                .build()

            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: throw IllegalStateException("Empty response from Imgur")

            val data = JSONObject(responseBody).getJSONObject("data")
            val imageUrl = data.getString("link")
            emit(Result.success(imageUrl))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    // Create a new user account
    suspend fun createUserAccount(email: String, password: String): Flow<Result<String>> = flow {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw IllegalStateException("Failed to create user")

            // Create initial user profile matching User data class
            val newUser = User(
                uid = userId,
                name = email.substringBefore("@"),
                email = email,
                profileImageUrl = null,
                createdAt = System.currentTimeMillis()
            )

            usersCollection.document(userId).set(newUser).await()
            emit(Result.success(userId))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
