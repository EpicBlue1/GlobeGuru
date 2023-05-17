package com.example.globeguru.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globeguru.R
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appLightGray

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkGray), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(appLightGray)
            .height(100.dp)) {
            Image(modifier = Modifier.padding(15.dp)
,               painter = painterResource(id = R.drawable.logo_android),
                contentDescription = "Logo")
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    GlobeGuruTheme {
        LoginScreen()
    }
}