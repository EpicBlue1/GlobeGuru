package com.example.globeguru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.screens.ConversationScreen
import com.example.globeguru.screens.LoginScreen
import com.example.globeguru.screens.RegisterScreen
import com.example.globeguru.ui.theme.GlobeGuruTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val authViewModel: AuthViewModel = viewModel(modelClass = AuthViewModel::class.java)


            GlobeGuruTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    Navigation(authViewModel = authViewModel)
//                    ConversationScreen()
//                    LoginScreen() {
//
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GlobeGuruTheme {
        Greeting("Android")
    }
}