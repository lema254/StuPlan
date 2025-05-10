
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.stuplan.models.User
import com.example.stuplan.repsitory.UserRepository
import android.content.Context
import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    internal val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _profileUpdatedSuccessfully = MutableStateFlow(false)
    val profileUpdatedSuccessfully: StateFlow<Boolean> = _profileUpdatedSuccessfully.asStateFlow()

    fun loadUserProfile(userId: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val targetUserId = userId ?: userRepository.getCurrentUser()
                ?: throw IllegalStateException("No user logged in")

                userRepository.getUserProfile(targetUserId).collect { result ->
                    result.fold(
                        onSuccess = { user ->
                            Log.d("ProfileViewModel", "Loaded user: $user")
                            _userProfile.value = user
                        },
                        onFailure = { error ->
                            _errorMessage.value = "Failed to load profile: ${error.message}"
                        }
                    )
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(updatedUser: User) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _profileUpdatedSuccessfully.value = false

            try {
                userRepository.updateUserProfile(updatedUser).collect { result ->
                    result.fold(
                        onSuccess = {
                            _userProfile.value = null
                            _userProfile.value = updatedUser
                            _profileUpdatedSuccessfully.value = true
                            Log.d("ProfileViewModel", "Profile updated successfully")
                        },
                        onFailure = { error ->
                            _errorMessage.value = "Failed to update profile: ${error.message}"
                        }
                    )
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadProfileImage(imageFile: File) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                userRepository.uploadImageToImgur(imageFile).collect { result ->
                    result.fold(
                        onSuccess = { imageUrl ->
                            val updatedUser = _userProfile.value?.copy(profileImageUrl = imageUrl)
                            if (updatedUser != null) {
                                updateProfile(updatedUser)
                            }
                        },
                        onFailure = { error ->
                            _errorMessage.value = "Image upload failed: ${error.message}"
                        }
                    )
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}