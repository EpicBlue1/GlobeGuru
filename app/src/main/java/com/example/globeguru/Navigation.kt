package com.example.globeguru

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.screens.ConversationScreen
import com.example.globeguru.screens.LoginScreen
import com.example.globeguru.screens.RegisterScreen

enum class AuthRoutes {
    Login,
    Register,
}

enum class Routes {
    Conversations,
    Chat,
    Profile,
    AddConversation,
    Search
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
){
    val startingScreen = if(authViewModel.hashUser){
        Routes.Conversations.name
    } else {
        AuthRoutes.Login.name
    }
    NavHost(navController = navController, startDestination = startingScreen){

        composable(route = AuthRoutes.Login.name){
            LoginScreen(navToRegister = {
                navController.navigate(AuthRoutes.Register.name){
//                    launchSingleTop = true
//                    popUpTo(route = AuthRoutes.Login.name){
//                        inclusive = true
//                    }
                }
            },
                navToHome = {
                    navController.navigate(Routes.Conversations.name){
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Login.name)
                    }
                },
                authViewModel = authViewModel)
        }

        composable(route = AuthRoutes.Register.name) {
            RegisterScreen(navToLogin = {
                navController.navigate(AuthRoutes.Login.name) {
                    launchSingleTop = true
                    popUpTo(route = AuthRoutes.Register.name) {
                        inclusive = true
                    }
                }
            },   navToHome = {
                navController.navigate(Routes.Conversations.name){
                    launchSingleTop = true
                    popUpTo(route = AuthRoutes.Register.name)
                }
            },
                authViewModel = authViewModel)
        }
        composable(route = Routes.Conversations.name){
            ConversationScreen()
        }
    }
}