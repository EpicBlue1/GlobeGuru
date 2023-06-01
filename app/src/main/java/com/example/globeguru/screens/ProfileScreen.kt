package com.example.globeguru.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.gandiva.neumorphic.shape.RoundedCorner

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel? = null,
    navSignOut: ()-> Unit,
    navBack:() -> Unit
){

    val defaultCornerShape: CornerShape = RoundedCorner(12.dp)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray)
        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
        ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .background(color = appDarkGray),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Box(modifier = Modifier
                    .clickable { navBack.invoke() }
                    .neu(
                        lightShadowColor = appLightGray,
                        darkShadowColor = appDarkerGray,
                        shadowElevation = 4.dp,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(defaultCornerShape)
                    )
//                    .background(color = appLightGray)
                    .border(width = 2.dp, color = appLightGray, shape = RoundedCornerShape (20))
                ){
                    Image(modifier = Modifier
                        .height(50.dp)
                        ,painter = painterResource(id = R.drawable.logo_android),
                        contentDescription = "add icon")
                }
            }
            Image(modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .height(85.dp)
                ,painter = painterResource(id = R.drawable.logo_android),
                contentDescription = "add icon")
            Text(modifier = Modifier.padding(10.dp), text = "Username", style = MaterialTheme.typography.titleLarge, color = appWhite)
            Text(modifier = Modifier.padding(10.dp), text = "Email", style = MaterialTheme.typography.titleSmall, color = appWhite)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                onClick = { AuthRepos().signOut(); navSignOut.invoke() },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appNewBlue)
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
                    Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Sign Out")
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