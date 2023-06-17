package com.example.globeguru.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.globeguru.ViewModels.AuthUiState
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.models.Flags
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appBlue
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appDarkerGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appNewBlue
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

    val uathUiState: AuthUiState? = authViewModel?.authUiState

    //Dropdown
    val options = listOf(
        Flags(R.drawable.cananda, "canada", "#R8UGN3"),
        Flags(R.drawable.us, "us", "#FG78UH"),
        Flags(R.drawable.southafrica, "sa", "#HOIML4"),
        Flags(R.drawable.norway, "norway", "#55PP9K")
    )
    var selectedFlag by remember { mutableStateOf<Flags?>(null) }

    val defaultCornerShape: CornerShape = RoundedCorner(10.dp)
    val context = LocalContext.current
    val loading = uathUiState?.isLoading

    val error = uathUiState?.errorMessage != null

    fun Register(){
        selectedFlag?.let { authViewModel?.handleStateChanges("registerCityCode", it.countryCode) }
        authViewModel?.createNewUser(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        .height(130.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.FillWidth)
                Text(modifier = Modifier
                    .padding(45.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center, text = "A new journey awaits", style = MaterialTheme.typography.titleLarge, color = appWhite)
            }
        }

        if(error){
            Text(text = uathUiState?.errorMessage.toString(), color = Color.Red)
        }

        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .weight(2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier) {
                OutlinedTextField(
                    value = uathUiState?.registerUsername ?: "",
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
                    value = uathUiState?.registerEmail ?: "",
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

                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Traveller?", color = appWhite)
                    Switch(
                        checked = uathUiState?.registerTraveler.toBoolean(), onCheckedChange = {
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

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), text = if (uathUiState?.registerTraveler.toBoolean()) "Destination:" else "Origin:", color = appWhite)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    options.forEach { flag ->
                        val isSelected = flag == selectedFlag
                        Box(modifier = Modifier
                            .width(46.dp)
                            .height(46.dp)
                            .border(
                                BorderStroke(
                                    4.dp,
                                    color = if (isSelected) appNewBlue else Color.Transparent
                                ), shape = CircleShape
                            )) {
                            Image(
                                modifier = Modifier
                                    .width(46.dp)
                                    .clickable {
                                        selectedFlag = if (isSelected) null else flag; Log.d(
                                        "BBB Image selected",
                                        selectedFlag?.country ?: " "
                                    ); authViewModel?.handleStateChanges(
                                        "registerCity",
                                        selectedFlag?.country.toString()
                                    )
                                    }
                                    .height(46.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                                painter = painterResource(id = flag.imageId),
                                contentDescription = flag.toString()
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = uathUiState?.registerPassword ?: "",
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
                    value = uathUiState?.registerConPassword ?: "",
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

            if(loading == true){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(modifier = Modifier)
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
                    onClick = { Register() },
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

fun DropdownMenuItem(text: () -> Unit, onClick: () -> Unit, interactionSource: () -> Unit) {

}

@Preview(showSystemUi = true)
@Composable
fun PreviewRegisterScreen() {
    GlobeGuruTheme {
        RegisterScreen(navToLogin = {}, navToHome = {}, authViewModel = AuthViewModel())
    }
}