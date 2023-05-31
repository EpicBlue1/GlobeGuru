package com.example.globeguru.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.globeguru.R
import com.example.globeguru.ViewModels.AuthViewModel
import com.example.globeguru.models.Conversations
import com.example.globeguru.ui.theme.GlobeGuruTheme
import com.example.globeguru.ui.theme.appBlue
import com.example.globeguru.ui.theme.appDarkGray
import com.example.globeguru.ui.theme.appLightGray
import com.example.globeguru.ui.theme.appWhite
import com.example.globeguru.ui.theme.semGreen
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Punched

@Composable
fun ConversationCard(
    conversation: Conversations,
    authViewModel: AuthViewModel? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 4.dp,
                color = appLightGray,
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(conversation.image)
                .crossfade(true)
                .build(),
            contentDescription = conversation.name,
            modifier = Modifier
                .fillMaxWidth().padding(3.dp)
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
            Text(text = conversation.totalMessages.toString(), modifier = Modifier.fillMaxWidth().zIndex(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(conversation.countryImage)
                .crossfade(true)
                .build(),
            contentDescription = conversation.name,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = appLightGray,
                    shape = CircleShape)
                .width(27.dp)
                .height(27.dp),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
        )
    }
}

@Preview
@Composable
fun ConversationCardPreview(){
    GlobeGuruTheme() {
        ConversationCard(conversation = Conversations())
    }
}