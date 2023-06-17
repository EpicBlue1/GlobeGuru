package com.example.globeguru.screens

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.globeguru.R
import com.example.globeguru.ViewModels.ConvoViewModel
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.Flags
import com.example.globeguru.models.User
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.globeguru.ViewModels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddScreen(
    viewModel: ConvoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navBack: () -> Unit,
    onNavToChat: (chatId: String)->Unit,
    profileViewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
){

    val profileUiState = profileViewModel.profileUiState
    var code by remember { mutableStateOf("") }
    var allChats by remember {
        mutableStateOf(listOf<User>())
    }

    Log.d("DD", profileUiState.toString())

    allChats = viewModel.chatList ?: listOf<User>()

    fun getFilteredChats(){
        Log.d("DDD code", code)
        viewModel.getChats(code)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth()){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp)) {
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
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(R.drawable.background)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(top = 20.dp)
                        .height(90.dp),
                    contentScale = ContentScale.FillWidth)
                Text(modifier = Modifier
                    .padding(45.dp, top = 60.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center, text = "Add by ticket code", style = MaterialTheme.typography.titleLarge, color = appWhite)
            }
        }

        Row(modifier = Modifier.padding(10.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .height(2.dp)
                .border(
                    width = 4.dp,
                    color = appLightGray,
                    shape = RoundedCornerShape(5.dp)
                )) {
            }
        }

        Column() {
            Image(painter = painterResource(id = R.drawable.ticket),
                contentDescription = "Ticket Preview",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        }

        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .weight(2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = code,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.Black
                ),
                onValueChange = { code = it },
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
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .weight(2f)
                ,
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            items(allChats){
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

        Column(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .height(80.dp), verticalArrangement = Arrangement.SpaceBetween) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { getFilteredChats() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                            .padding(top = 2.dp),
                        contentScale = ContentScale.FillHeight,
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Logo"
                    )
                    Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Search Code", color = appWhite)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddScreenPreview(){
    GlobeGuruTheme {
        AddScreen(navBack = {}, onNavToChat = {})
    }
}