package com.example.globeguru.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.globeguru.R
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appBlue
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appDarkerGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.CornerShape
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel? = null,
    modifier: Modifier = Modifier,
    navToRegister:()-> Unit,
    navToHome:()-> Unit
){
    //get values from viewModel
    val authUiState = authViewModel?.authUiState
    val error = authUiState?.errorMessage != null
    val loading = authUiState?.isLoading
    val context = LocalContext.current
    val defaultCornerShape: CornerShape = RoundedCorner(12.dp)

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight()
        .verticalScroll(rememberScrollState())
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.fillMaxWidth()){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(R.drawable.background)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Background",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(194.dp),
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        contentScale = ContentScale.FillWidth)
                    Text(modifier = Modifier
                        .padding(45.dp)
                        .fillMaxWidth(), textAlign = TextAlign.Center, text = "Heya User!", style = MaterialTheme.typography.titleLarge, color = appWhite)
                }
        }

        if(error){
            Text(text = authUiState?.errorMessage.toString(), color = Color.Red)
        }

        Column(modifier = Modifier
            .padding(30.dp)
//            .height(300.dp)
            .fillMaxSize()
            .weight(2f), verticalArrangement = Arrangement.SpaceBetween
            ) {

            Column(modifier = Modifier.height(250.dp), verticalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = authUiState?.loginEmail ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = {authViewModel?.handleStateChanges("loginEmail", it)},
                    placeholder = {Text(text = "Email")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .neu(
                            lightShadowColor = appLightGray,
                            darkShadowColor = appDarkerGray,
                            shadowElevation = 6.dp,
                            lightSource = LightSource.LEFT_TOP,
                            shape = Pressed(RoundedCorner(10.dp)),
                        )
                )

                OutlinedTextField(
                    value = authUiState?.loginPassword ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = {authViewModel?.handleStateChanges("loginPassword", it)},
                    placeholder = {Text(text = "Password")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .neu(
                            lightShadowColor = appLightGray,
                            darkShadowColor = appDarkerGray,
                            shadowElevation = 6.dp,
                            lightSource = LightSource.LEFT_TOP,
                            shape = Pressed(RoundedCorner(10.dp)),
                        )
                )

                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Need an account?", color = appWhite, style = MaterialTheme.typography.bodyMedium)
                    TextButton(onClick = {navToRegister.invoke()}) {
                        Text(text = "Register", color = appBlue, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            if(loading == true){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(modifier = Modifier)
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = SpaceBetween) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .neu(
                            lightShadowColor = appLightGray,
                            darkShadowColor = appDarkerGray,
                            shadowElevation = 6.dp,
                            lightSource = LightSource.LEFT_TOP,
                            shape = Flat(defaultCornerShape)
                        ),
                    onClick = { authViewModel?.loginUser(context) },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appDarkGray)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(modifier = Modifier
                            .height(32.dp)
                            .width(32.dp)
                            .padding(top = 2.dp),
                            contentScale = ContentScale.FillHeight,
                            painter = painterResource(id = R.drawable.logo_android),
                            contentDescription = "Logo")
                        Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Log In")
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = authViewModel?.hashUser){
        if(authViewModel?.hashUser == true){
            navToHome.invoke()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    GlobeGuruTheme {
        LoginScreen(navToRegister = {}, navToHome = {})
    }
}