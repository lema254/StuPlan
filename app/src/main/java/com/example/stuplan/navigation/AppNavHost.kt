package com.example.stuplan.navigation

import LessonScreen1
import ProfileScreen
import ProfileViewModel
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stuplan.data.AuthViewModel


import com.example.stuplan.ui.theme.screens.SplashScreen

import com.example.stuplan.ui.theme.screens.contentscreens.HTMLScreen
import com.example.stuplan.ui.theme.screens.contentscreens.JavaScreen
import com.example.stuplan.ui.theme.screens.contentscreens.KotlinScreen
import com.example.stuplan.ui.theme.screens.contentscreens.PythonScreen
import com.example.stuplan.ui.theme.screens.contentscreens.SwiftScreen
import com.example.stuplan.ui.theme.screens.dashboard.DashboardScreen
import com.example.stuplan.ui.theme.screens.lessons.LessonScreen2


import com.example.stuplan.ui.theme.screens.login.LoginScreen



import com.example.stuplan.ui.theme.screens.register.RegisterScreen

@Composable
fun AppNavHost(navController:NavHostController = rememberNavController(),startDestination:String= ROUTE_SPLASH){
    NavHost(navController=navController,startDestination=startDestination){
        composable(ROUTE_SPLASH){ SplashScreen {
            navController.navigate(ROUTE_REGISTER) {
                popUpTo(ROUTE_SPLASH){inclusive=true}} }}
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
        composable(ROUTE_LOGIN) { LoginScreen(navController) }
        composable(ROUTE_DASHBOARD){ DashboardScreen(navController) }
        composable(ROUTE_SWIFT){ SwiftScreen(navController) }
        composable(ROUTE_PYTHON){ PythonScreen(navController) }
        composable(ROUTE_KOTLIN){ KotlinScreen(navController) }
        composable(ROUTE_JAVA){ JavaScreen(navController) }
        composable(ROUTE_HTML){ HTMLScreen(navController) }
        composable(ROUTE_PROFILE) { ProfileScreen(
            navController,
            userId = null,
            onNavigateToEdit = {},
            viewModel = TODO(),
            onNavigateBack = TODO()
        ) }
        composable(ROUTE_LESSON1) { LessonScreen1(
            navController,
            moduleId = TODO()
        ) }
        composable(ROUTE_LESSON2) {
            LessonScreen2(
                navController,
                moduleId = TODO()
            )
        }
       
        }

        }
    









