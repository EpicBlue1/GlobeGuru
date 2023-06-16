package com.example.globeguru.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.globeguru.R
import com.example.globeguru.ViewModels.ConvoViewModel
import com.example.globeguru.models.Conversations
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appDarkerGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite
import com.example.globeguru.ui.theme.semGreen
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.RoundedCorner

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversationScreen(
    viewModel: ConvoViewModel = viewModel(),
    navToProfile: () -> Unit,
    onNavToChat: (chatId: String)->Unit,
){
    val allChats = viewModel?.chatList ?: listOf<Conversations>()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(110.dp),
//            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround
        verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(text = "GlobeGuru", style = MaterialTheme.typography.titleMedium,
//                color = appWhite)
            Row(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                Image(modifier = Modifier
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
                    ,painter = painterResource(id = R.drawable.profiletemp),
                    contentDescription = "dd icon")
                Column(modifier = Modifier
                    .padding(start = 35.dp, top = 5.dp)
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
                            Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Add someone")
                        }
                    }
                    Text(modifier = Modifier.fillMaxWidth(), text = "Total Chats: " + allChats.count(), color = appWhite, textAlign = TextAlign.Start)
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

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize()
                .weight(2f),
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            items(allChats){chat ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(
                            width = 4.dp,
                            color = appLightGray,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { onNavToChat.invoke(chat.id) }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(chat.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = chat.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(194.dp),
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        contentScale = ContentScale.FillHeight)
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .background(color = semGreen)
                        .padding(1.dp)
                        .width(25.dp)
                        .height(25.dp)
                    ) {
                        Text(text = chat.totalMessages.toString(), modifier = Modifier
                            .fillMaxWidth()
                            .zIndex(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(chat.countryImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = chat.name,
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
        Row(modifier = Modifier.height(64.dp).fillMaxWidth().background(color = appDarkerGray), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
            Box(modifier = Modifier.height(45.dp).width(45.dp).clip(shape = RoundedCornerShape(20)).background(color = Color.Transparent)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(R.drawable.add_icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = "NonTravel",
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                )
            }
            Box(modifier = Modifier.height(46.dp).width(45.dp).clip(shape = RoundedCornerShape(20)).background(color = appDarkGray)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(R.drawable.add_icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Travel",
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConversationScreenPreview(){
    GlobeGuruTheme {
        ConversationScreen(navToProfile={}, onNavToChat = {})
    }
}