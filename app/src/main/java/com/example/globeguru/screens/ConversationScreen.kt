package com.example.globeguru.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.globeguru.R
import com.example.globeguru.ViewModels.ConvoViewModel
import com.example.globeguru.ViewModels.ProfileViewModel
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.User
import com.example.globeguru.models.data
import com.example.globeguru.repositories.AuthRepos
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appDarkerGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite
import com.example.globeguru.ui.theme.semGreen
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    viewModel: ConvoViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    navToProfile: () -> Unit,
    onNavToChat: (chatId: String)->Unit,
    onNavToAdd: () -> Unit,
    authRepos: AuthRepos = AuthRepos()
){
    val allChats = viewModel.ownchatList ?: listOf<User>()
    Log.d("TEST EIER JISS", allChats.toString())

    val profileUiState = profileViewModel.profileUiState
    val currentUserId = authRepos.getUserId()

    val dataState = remember { mutableStateOf<Any>("") }
    val shouldExecuteEffect = remember { mutableStateOf(false) }

    LaunchedEffect(shouldExecuteEffect.value){
        if (shouldExecuteEffect.value) {
            // Code to be executed when the effect is launched

            // For example, fetch data from a remote source
            val data = viewModel.getOwnChats(profileUiState.traveler.toBoolean(), currentUserId)
            // Update the state with the fetched data
            dataState.value = data
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(110.dp),
        verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                AsyncImage(modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clickable { navToProfile.invoke() }
                    .neu(
                        lightShadowColor = appLightGray,
                        darkShadowColor = appDarkerGray,
                        shadowElevation = 4.dp,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(RoundedCorner(100.dp))
                    )
                    .clip(CircleShape)
                    ,contentScale = ContentScale.Crop
                    ,model = ImageRequest.Builder(context = LocalContext.current)
                        .data(profileUiState.profileImage ?: R.drawable.tempprofile)
                        .crossfade(true)
                        .build(),
                    contentDescription = "profile icon")

                Column(modifier = Modifier
                    .padding(start = 35.dp, top = 5.dp)
                    .height(80.dp), verticalArrangement = Arrangement.Center) {
                    if(profileUiState.traveler.toBoolean()){
                        var top = 10
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
                            onClick = { "" },
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
                                Text(modifier = Modifier.clickable{onNavToAdd.invoke()}, fontWeight = FontWeight.Bold, text = "Add connection", color = appWhite)
                            }
                        }
                    }
                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if (profileUiState.traveler.toBoolean()) 10.dp else 0.dp), text = "Total Chats: " + allChats.count(), color = appWhite, textAlign = TextAlign.Start)
                }
            }
        }
        
        Row(modifier = Modifier.padding(10.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .border(
                    width = 4.dp,
                    color = appLightGray,
                    shape = RoundedCornerShape(5.dp)
                )) {
                
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
            onClick = { shouldExecuteEffect.value = true },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = appDarkGray
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Refresh", color = appWhite)
            }
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize()
                .weight(2f),
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            items(allChats){
                Log.d("FFF", it.toString())
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(
                            width = 4.dp,
                            color = appLightGray,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { onNavToChat.invoke(it.id) }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(it.profileImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = it.username,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(194.dp),
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        contentScale = ContentScale.FillHeight)
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(if (it.city == "sa") R.drawable.southafrica
                            else if (it.city == "us") R.drawable.us
                            else if (it.city == "norway") R.drawable.norway
                            else if (it.city == "canada") R.drawable.cananda else R.drawable.logo_android)
                            .crossfade(true)
                            .build(),
                        contentDescription = "chat.name",
                        modifier = Modifier
                            .align(alignment = Alignment.BottomEnd)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = appLightGray,
                                shape = CircleShape
                            )
                            .width(27.dp)
                            .height(27.dp),
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    )
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConversationScreenPreview(){
    GlobeGuruTheme {
        ConversationScreen(navToProfile={}, onNavToChat = {}, onNavToAdd = {})
    }
}