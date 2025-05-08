package com.example.stuplan.ui.theme.screens.register


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stuplan.data.AuthViewModel
import com.example.stuplan.data.UserViewModel

// Define our theme colors to match the reference design
val PrimaryOrange = Color(0xFFFF6B35)
val BackgroundPeach = Color(0xFFFFE0D0)
val TextDark = Color(0xFF1D1D1D)
val White = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val authViewModel: AuthViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PrimaryOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SP",
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Code Academy",
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundPeach,
                    titleContentColor = TextDark
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPeach)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // User avatar placeholder
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(PrimaryOrange.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "User",
                        tint = PrimaryOrange,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Create Your Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = "Join our coding community and start learning",
                    fontSize = 14.sp,
                    color = TextDark.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Registration form card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Name field
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            placeholder = { Text("Enter your full name") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Name",
                                    tint = PrimaryOrange
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = TextDark.copy(alpha = 0.2f),
                                focusedLabelColor = PrimaryOrange,
                                unfocusedLabelColor = TextDark.copy(alpha = 0.6f)
                            )
                        )

                        // Email field
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email Address") },
                            placeholder = { Text("Enter your email address") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = PrimaryOrange
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = TextDark.copy(alpha = 0.2f),
                                focusedLabelColor = PrimaryOrange,
                                unfocusedLabelColor = TextDark.copy(alpha = 0.6f)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )

                        // Password field
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            placeholder = { Text("Create a strong password") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Password",
                                    tint = PrimaryOrange
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = TextDark.copy(alpha = 0.2f),
                                focusedLabelColor = PrimaryOrange,
                                unfocusedLabelColor = TextDark.copy(alpha = 0.6f)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        // Confirm Password field
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm Password") },
                            placeholder = { Text("Confirm your password") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Confirm Password",
                                    tint = PrimaryOrange
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = TextDark.copy(alpha = 0.2f),
                                focusedLabelColor = PrimaryOrange,
                                unfocusedLabelColor = TextDark.copy(alpha = 0.6f)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        // Bio field
                        OutlinedTextField(
                            value = bio,
                            onValueChange = { bio = it },
                            label = { Text("Bio") },
                            placeholder = { Text("Tell us about yourself") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Bio",
                                    tint = PrimaryOrange
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = TextDark.copy(alpha = 0.2f),
                                focusedLabelColor = PrimaryOrange,
                                unfocusedLabelColor = TextDark.copy(alpha = 0.6f)
                            )
                        )

                        // Terms and conditions checkbox
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = termsAccepted,
                                onCheckedChange = { termsAccepted = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = PrimaryOrange,
                                    uncheckedColor = TextDark.copy(alpha = 0.6f)
                                )
                            )
                            Text(
                                text = "I accept the Terms and Conditions",
                                fontSize = 14.sp,
                                color = TextDark.copy(alpha = 0.8f)
                            )
                        }

                        // Register button
                        Button(
                            onClick = {
                                if (termsAccepted && password == confirmPassword) {
                                    viewModel.register(name, email, bio)
                                    navController.navigate("dashboard") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                            shape = RoundedCornerShape(24.dp),
                            enabled = name.isNotBlank() && email.isNotBlank() &&
                                    password.isNotBlank() && confirmPassword.isNotBlank() &&
                                    password == confirmPassword && termsAccepted
                        ) {
                            Text(
                                text = "Create Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Divider with "or" text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = TextDark.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "  OR  ",
                        fontSize = 14.sp,
                        color = TextDark.copy(alpha = 0.6f)
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = TextDark.copy(alpha = 0.2f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Social sign-up buttons
                Button(
                    onClick = { /* Google sign-up */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Google icon placeholder
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign up with Google",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* GitHub sign-up */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TextDark
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // GitHub icon placeholder
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign up with GitHub",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Already have an account link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account?",
                        fontSize = 14.sp,
                        color = TextDark.copy(alpha = 0.8f)
                    )
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text(
                            text = "Sign In",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrange
                        )
                    }
                }

                // Additional spacing at the bottom
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}