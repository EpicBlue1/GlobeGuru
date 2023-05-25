package com.example.globeguru.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globeguru.R
import com.example.globeguru.composables.ConversationCard
import com.example.globeguru.models.Conversations
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversationScreen(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = appLightGray),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Image(modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .height(80.dp)
                ,painter = painterResource(id = R.drawable.logo_android),
                contentDescription = "add icon")
            Text(modifier = Modifier.padding(30.dp), text = "Connections", style = MaterialTheme.typography.titleMedium, color = appWhite)
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(15.dp),
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            content = {
            items(count = 10){
            ConversationCard(Conversations(
                    name= "",
                    image= "https://www.splento.com/blog/wp-content/uploads/2020/02/16-1-1.jpg",
                    totalMessages = 0,
                    countryImage = "https://www.kindpng.com/picc/m/34-348331_united-kingdom-flag-icon-round-united-kingdom-flag.png"
                ))
            }
        })
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConversationScreenPreview(){
    GlobeGuruTheme {
        ConversationScreen()
    }
}