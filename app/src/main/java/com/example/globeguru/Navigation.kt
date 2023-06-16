package com.example.globeguru

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.screens.ChatScreen
import com.example.globeguru.screens.ConversationScreen
import com.example.globeguru.screens.LoginScreen
import com.example.globeguru.screens.ProfileScreen
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
            ConversationScreen(navToProfile = {
                navController.navigate(Routes.Profile.name) {
                    launchSingleTop = true
                }
            },
                onNavToChat = {
                    navController.navigate("${Routes.Chat.name}/${it}"){
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(route = Routes.Profile.name){
            ProfileScreen(navSignOut = {
            navController.navigate(AuthRoutes.Login.name){
                launchSingleTop = true
                popUpTo(route = Routes.Conversations.name)
            } }, navBack = {
                    navController.navigate(Routes.Conversations.name){
                        launchSingleTop = true
                        popUpTo(route = Routes.Conversations.name)
                    }
                }
            )
        }

        composable(route = "${Routes.Chat.name}/{chatId}",
            arguments = listOf(navArgument("chatId"){type = NavType.StringType; defaultValue = "chat123"})
            ){
            ChatScreen( chatId = it.arguments?.getString("chatId"))
        }
    }
}