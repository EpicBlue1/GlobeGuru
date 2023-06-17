package com.example.globeguru.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globeguru.R
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.repositories.AuthRepos
import com.example.globeguru.ui.theme.GlobeGuruTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.globeguru.ViewModels.ProfileViewModel
import com.example.globeguru.ui.theme.appBlue
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel? = null,
    navSignOut: ()-> Unit,
    navBack:() -> Unit,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){

    val profileUiState = viewModel?.profileUiState

    val defaultCornerShape: CornerShape = RoundedCorner(12.dp)

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { item ->
            if (item != null) {
                viewModel.handleProfileImgUpdate(item)
            }
        }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray)
        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
        ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = appDarkGray),
            verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 20.dp, top = 20.dp)) {
                Box(modifier = Modifier
                    .clickable { navBack.invoke() }
                    .neu(
                        lightShadowColor = appLightGray,
                        darkShadowColor = appDarkerGray,
                        shadowElevation = 4.dp,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(defaultCornerShape)
                    )
                    .border(width = 2.dp, color = appLightGray, shape = RoundedCornerShape(20))
                ){
                    Image(modifier = Modifier
                        .height(50.dp)
                        ,painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "back")
                }
            }

            Box(modifier = Modifier){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(profileUiState?.profileImage ?: R.drawable.tempprofile)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Travel",
                    modifier = Modifier
                        .width(110.dp)
                        .height(110.dp)
                        .neu(
                            lightShadowColor = appLightGray,
                            darkShadowColor = appDarkerGray,
                            shadowElevation = 6.dp,
                            lightSource = LightSource.LEFT_TOP,
                            shape = Flat(RoundedCorner(100.dp))
                        )
                        .clip(CircleShape)
                    ,contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                )
                Column(modifier = Modifier
                    .height(110.dp)
                    .width(110.dp)
                    .clip(CircleShape)
                    .background(color = appLightGray.copy(alpha = 0.6f))
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Image(modifier = Modifier
                        .height(64.dp)
                        .width(64.dp)
                        .clip(CircleShape)
                        ,contentScale = ContentScale.Inside
                        ,painter = painterResource(id = R.drawable.edit),
                        contentDescription = "profile icon")
                }
            }

            Row(modifier = Modifier.padding(3.dp).fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                Text(text = profileUiState?.userName ?: "", style = MaterialTheme.typography.titleLarge, color = appWhite)
                Image(modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)
                    .clip(CircleShape), painter = painterResource(id = if(profileUiState?.city == "southafrica") R.drawable.southafrica
                else if (profileUiState?.city == "us") R.drawable.us
                else if(profileUiState?.city == "norway") R.drawable.norway
                else if (profileUiState?.city == "sa") R.drawable.southafrica
                else R.drawable.logo_android), contentDescription = "Flag" )
            }

            Text(modifier = Modifier.padding(3.dp), text = profileUiState?.email ?: "", style = MaterialTheme.typography.titleSmall, color = appWhite)
        }

        Column(modifier = Modifier
            .padding(40.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .weight(2f)) {

            OutlinedTextField(
                value = profileUiState?.userName ?: "",
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.Black
                ),
                onValueChange = { viewModel.handleProfileStateChange(target = "userName", it) },
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
                value = profileUiState?.email ?: "",
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.Black
                ),
                enabled = false,
                onValueChange = { "" },
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
                    checked = profileUiState?.traveler.toBoolean(), onCheckedChange = {
                        viewModel.handleProfileStateChange(target = "traveler", it.toString())
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = appBlue,
                        uncheckedThumbColor = appDarkGray,
                        checkedTrackColor = appDarkerGray,
                        uncheckedTrackColor = appLightGray,
                    )
                )
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(start = 40.dp, end = 40.dp, bottom = 40.dp),

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
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
                onClick = { viewModel.saveProfileData() },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appDarkGray)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(modifier = Modifier
                        .height(32.dp)
                        .padding(top = 5.dp), fontWeight = FontWeight.Bold, text = "Save Changes", color = appWhite)
                }
            }

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
                onClick = { AuthRepos().signOut(); navSignOut.invoke() },
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
                    Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Sign Out", color = appWhite )
                }
            }
        }
        
    }

}

@Preview( showSystemUi = true)
@Composable
fun PreviewProfileScreen(){
    GlobeGuruTheme {
        ProfileScreen(navBack = {}, navSignOut = {})
    }
}