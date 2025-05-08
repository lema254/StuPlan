package com.example.stuplan.ui.theme.screens.login

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import com.example.stuplan.ui.theme.screens.dashboard.BackgroundPeach
import com.example.stuplan.ui.theme.screens.dashboard.PrimaryOrange
import com.example.stuplan.ui.theme.screens.dashboard.TextDark
import com.example.stuplan.ui.theme.screens.dashboard.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
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
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Welcome Back!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = "Sign in to continue your learning journey",
                    fontSize = 14.sp,
                    color = TextDark.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Login form card
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
                            placeholder = { Text("Enter your password") },
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

                        // Remember me and forgot password row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = PrimaryOrange,
                                        uncheckedColor = TextDark.copy(alpha = 0.6f)
                                    )
                                )
                                Text(
                                    text = "Remember me",
                                    fontSize = 14.sp,
                                    color = TextDark.copy(alpha = 0.8f)
                                )
                            }

                            TextButton(onClick = { /* Navigate to forgot password */ }) {
                                Text(
                                    text = "Forgot Password?",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = PrimaryOrange
                                )
                            }
                        }

                        // Login button
                        Button(
                            onClick = {
                                viewModel.login(email, password)
                                navController.navigate("dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                            shape = RoundedCornerShape(24.dp),
                            enabled = email.isNotBlank() && password.isNotBlank()
                        ) {
                            Text(
                                text = "Sign In",
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
                        text = "or",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = TextDark.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = TextDark.copy(alpha = 0.2f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Social login options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Social login buttons would go here
                    // Using placeholders for now

                    // Google login button
                    Button(
                        onClick = { /* Google login logic */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = TextDark
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Google",
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Facebook login button
                    Button(
                        onClick = { /* Facebook login logic */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = TextDark
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Facebook",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Registration prompt
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 14.sp,
                        color = TextDark.copy(alpha = 0.8f)
                    )

                    TextButton(onClick = { navController.navigate("register") }) {
                        Text(
                            text = "Sign Up",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrange
                        )
                    }
                }

                // Add some space at the bottom for better scrolling experience
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}