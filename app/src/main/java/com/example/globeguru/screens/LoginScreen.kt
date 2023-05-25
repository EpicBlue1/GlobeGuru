package com.example.globeguru.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.globeguru.R
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.ui.theme.CenturyGothic
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.QuickSand
import com.example.globeguru.ui.theme.appBlue
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel? = null,
    modifier: Modifier = Modifier,
    navToRegister:()-> Unit
){
    //get values from viewModel
    val uathUiState = authViewModel?.authUiState
    val error = uathUiState?.errorMessage != null
    val context = LocalContext.current


//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(color = appLightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

                Image(modifier = Modifier.padding(10.dp)
    ,               painter = painterResource(id = R.drawable.logo_android),
                    contentDescription = "Logo")

                Text(modifier = Modifier.padding(10.dp), text = "Heya User!", style = MaterialTheme.typography.titleLarge, color = appWhite)
        }

        if(error){
            Text(text = uathUiState?.errorMessage.toString(), color = Color.Red)
        }

        Column(modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {


            Column() {
                OutlinedTextField(
                    value = uathUiState?.loginEmail ?: "",
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
                        .drawBehind {
                            drawLine(
                                color = appBlue,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                        .padding(0.dp)
                )

                OutlinedTextField(
                    value = uathUiState?.loginPassword ?: "",
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
                        .drawBehind {
                            drawLine(
                                color = appBlue,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                        .padding(0.dp)
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




            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = SpaceBetween) {
                Button(
                    modifier = Modifier.width(145.dp),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appWhite,
                        contentColor = appDarkGray)
                ) {
                    Row(modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = SpaceBetween
                    ) {
                        Image(modifier = Modifier.height(20.dp),
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Logo")
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(text = "Sign In")
                    }
                }

                Button(
                    modifier = Modifier.width(145.dp),
                    onClick = { authViewModel?.loginUser(context) },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appBlue,
                        contentColor = appWhite)
                ) {
                    Text(text = "Sign In")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    GlobeGuruTheme {
        LoginScreen(navToRegister = {})
    }
}