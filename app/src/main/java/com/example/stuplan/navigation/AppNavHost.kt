package com.example.stuplan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stuplan.data.AuthViewModel
import com.example.stuplan.ui.theme.screens.EditProfileScreen
import com.example.stuplan.ui.theme.screens.ProfileScreen

import com.example.stuplan.ui.theme.screens.SplashScreen
import com.example.stuplan.ui.theme.screens.contentscreens.HTMLScreen
import com.example.stuplan.ui.theme.screens.contentscreens.JavaScreen
import com.example.stuplan.ui.theme.screens.contentscreens.KotlinScreen
import com.example.stuplan.ui.theme.screens.contentscreens.PythonScreen
import com.example.stuplan.ui.theme.screens.contentscreens.SwiftScreen
import com.example.stuplan.ui.theme.screens.dashboard.DashboardScreen
import com.example.stuplan.ui.theme.screens.login.LoginScreen

import com.example.stuplan.ui.theme.screens.register.RegisterScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    // 1️⃣ Get your AuthViewModel
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    // 2️⃣ Observe the current user
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = startDestination) {
        // Splash → Dashboard (or login on real check)
        composable(ROUTE_SPLASH) {
            SplashScreen {
                // If you want to redirect based on login:
                if (currentUser != null) {
                    navController.navigate(ROUTE_DASHBOARD) { popUpTo(ROUTE_SPLASH) { inclusive = true } }
                } else {
                    navController.navigate(ROUTE_LOGIN)     { popUpTo(ROUTE_SPLASH) { inclusive = true } }
                }
            }
        }

        // 3️⃣ Register
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController = navController,)
        }

        // 4️⃣ Login
        composable(ROUTE_LOGIN) {
            LoginScreen(
                navController = navController,

            )
        }

        // 5️⃣ Dashboard & Content Screens
        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        composable(ROUTE_KOTLIN) { KotlinScreen(navController) }
        composable(ROUTE_HTML)   { HTMLScreen(navController)   }
        composable(ROUTE_JAVA)   { JavaScreen(navController)   }
        composable(ROUTE_PROFILE)   { ProfileScreen(navController)   }
        composable(ROUTE_PYTHON)   { PythonScreen(navController)   }
        composable(ROUTE_SWIFT)   { SwiftScreen(navController)   }


        // 6️⃣ Profile (dynamic!)
        composable(ROUTE_EDITPROFILE) { EditProfileScreen(navController) }}}





