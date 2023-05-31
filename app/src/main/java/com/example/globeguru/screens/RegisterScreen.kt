package com.example.globeguru.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globeguru.R
import com.example.globeguru.ViewModels.AuthUiState
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
fun RegisterScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel? = null,
    navToLogin:()-> Unit,
    navToHome:()-> Unit
) {

    val authUiState: AuthUiState? = authViewModel?.authUiState

    val defaultCornerShape: CornerShape = RoundedCorner(12.dp)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier
                    .background(color = appDarkGray)
                    .neu(
                        lightShadowColor = appLightGray,
                        darkShadowColor = appDarkerGray,
                        shadowElevation = 6.dp,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(defaultCornerShape),
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = appLightGray,
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(80.dp), painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "add icon"
                )
            }
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Upload Profile",
                style = MaterialTheme.typography.titleMedium,
                color = appWhite
            )

        }
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column() {
                OutlinedTextField(
                    value = authUiState?.registerUsername ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = { authViewModel?.handleStateChanges("registerUsername", it) },
                    placeholder = { Text(text = "Name and Surname") },
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
                    value = authUiState?.registerEmail ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = { authViewModel?.handleStateChanges("registerEmail", it) },
                    placeholder = { Text(text = "Email") },
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
                    value = authUiState?.registerCity ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = { authViewModel?.handleStateChanges("registerCity", it) },
                    placeholder = { Text(text = "City") },
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

                Row(
                    modifier = Modifier.height(60.dp).fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Traveller?", color = appWhite)
                    Switch(
                        checked = authUiState?.registerTraveler.toBoolean(), onCheckedChange = {
                            authViewModel?.handleStateChanges(
                                "registerTraveler",
                                it.toString()
                            )
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = appBlue,
                            uncheckedThumbColor = appDarkGray,
                            checkedTrackColor = appDarkerGray,
                            uncheckedTrackColor = appLightGray,
                        )
                    )
                }

                OutlinedTextField(
                    value = authUiState?.registerPassword ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = { authViewModel?.handleStateChanges("registerPassword", it) },
                    placeholder = { Text(text = "Password") },
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
                    value = authUiState?.registerConPassword ?: "",
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = {
                        authViewModel?.handleStateChanges(
                            "registerConPassword",
                            it
                        )
                    },
                    placeholder = { Text(text = "Confirm Password") },
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account?",
                        color = appWhite,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    TextButton(onClick = { navToLogin.invoke() }) {
                        Text(
                            text = "Log In",
                            color = appBlue,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
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
                    onClick = { authViewModel?.createNewUser(context) },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appDarkGray
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp)
                                .padding(top = 2.dp),
                            contentScale = ContentScale.FillHeight,
                            painter = painterResource(id = R.drawable.userplus),
                            contentDescription = "Logo"
                        )
                        Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Sign Up")
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = authViewModel?.hashUser) {
        if (authViewModel?.hashUser == true) {
            navToHome.invoke()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewRegisterScreen() {
    GlobeGuruTheme {
        RegisterScreen(navToLogin = {}, navToHome = {}, authViewModel = AuthViewModel())
    }
}