package com.example.globeguru.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import com.example.globeguru.R
import com.example.globeguru.ViewModels.ConvoViewModel
import com.example.globeguru.composables.ConversationCard
import com.example.globeguru.models.Conversations
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appDarkerGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.RoundedCorner

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversationScreen(
    viewModel: ConvoViewModel = ConvoViewModel(),
    navToProfile: () -> Unit
){
    val allChats = viewModel?.chatList ?: listOf<Conversations>()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
            Image(modifier = Modifier
                .height(55.dp)
                .width(55.dp)
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
                contentDescription = "dd icona")
            Box(modifier = Modifier.width(200.dp)
                .neu(
                    lightShadowColor = appLightGray,
                    darkShadowColor = appDarkerGray,
                    shadowElevation = 4.dp,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Flat(RoundedCorner(10.dp))
                )
                .clip(RoundedCornerShape(10.dp))
                .background(color = appLightGray)
                .height(55.dp),
                contentAlignment = Alignment.Center){
                Text(modifier = Modifier.zIndex(1f),
                    textAlign = TextAlign.Center,
                    text = "Connections",
                    style = MaterialTheme.typography.titleMedium,
                    color = appWhite)
            }
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(15.dp),
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            items(allChats){chat ->
                ConversationCard(Conversations(
                    name= chat.name,
                    image= chat.image,
                    totalMessages = chat.totalMessages,
                    countryImage = chat.countryImage
                ))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConversationScreenPreview(){
    GlobeGuruTheme {
        ConversationScreen(navToProfile = {})
    }
}