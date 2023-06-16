package com.example.globeguru.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globeguru.R
import com.example.globeguru.ViewModels.ChatViewModel
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.Message
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

val defaultCornerShape: CornerShape = RoundedCorner(12.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier,
               chatId: String?,
               navBack:()-> Unit,
               viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel())
    {

        var newMessage: String by remember {
            mutableStateOf("")
        }

        val isChatIdBlank = chatId.isNullOrBlank()

        LaunchedEffect(key1 = Unit){
            if(!isChatIdBlank){
                Log.d("AA Receive Messages", "Umm")
                viewModel.realTimeMessages(chatId ?: "")
            } else {
                Log.d("AAA chat id error", chatId.toString())
            }
        }

        val allMessages = viewModel?.messageList ?: listOf<Message>()
        Log.d("LOL", allMessages.toList().toString())

    Column(modifier = Modifier

        .background(color = appLightGray), verticalArrangement = Arrangement.SpaceBetween) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(appDarkGray)
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically ) {

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
                    .border(width = 2.dp, color = appLightGray, shape = RoundedCornerShape(20))
                ){
                    Image(modifier = Modifier
                        .height(50.dp)
                        ,painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "back")
                }

                Text(text = chatId ?: "", modifier = Modifier, style = MaterialTheme.typography.titleMedium, color = appWhite)

                Image(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .neu(
                        lightShadowColor = appLightGray,
                        darkShadowColor = appDarkerGray,
                        shadowElevation = 6.dp,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(RoundedCorner(100.dp))
                    )
                    .clip(CircleShape)
                    ,contentScale = ContentScale.Crop
                    ,painter = painterResource(id = R.drawable.profiletemp),
                    contentDescription = "add icon")
            }
            LazyColumn(modifier = Modifier
                .weight(2f)
                .padding(end = 10.dp, start = 10.dp)
                , reverseLayout = true){
                items(allMessages){ message ->
                    if(viewModel.currentUserId == message.fromUser){
                        RightChat(message)
                    } else {
                        LeftChat(message)
                    }
                }
            }

            Row(modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                .fillMaxWidth()
                .height(80.dp)
                .background(color = appDarkGray)
                .padding(10.dp)
                , horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newMessage,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    onValueChange = {newMessage = it},
                    placeholder = {Text(text = "Type a message...")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .height(60.dp)
                        .neu(
                            lightShadowColor = appLightGray,
                            darkShadowColor = appDarkerGray,
                            shadowElevation = 6.dp,
                            lightSource = LightSource.LEFT_TOP,
                            shape = Pressed(RoundedCorner(20.dp)),
                        )
                )
                Box(modifier = Modifier
                    .clickable {
                        viewModel.sendNewMessage(newMessage, chatId ?: "")
                        newMessage = ""
                    }
                    .border(width = 2.dp, color = appNewBlue, shape = RoundedCornerShape(20))
                ){
                    Image(modifier = Modifier
                        .height(60.dp)
                        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                        ,painter = painterResource(id = R.drawable.send_icon),
                        contentDescription = "back")
                }
            }
    }
}

@Composable
fun LeftChat(message: Message){
    Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp), verticalAlignment = Alignment.Bottom) {
        Image(modifier = Modifier
            .height(25.dp)
            .width(25.dp)
            .neu(
                lightShadowColor = appLightGray,
                darkShadowColor = appDarkerGray,
                shadowElevation = 3.dp,
                lightSource = LightSource.LEFT_TOP,
                shape = Flat(RoundedCorner(100.dp))
            )
            .clip(CircleShape)
            ,contentScale = ContentScale.Crop
            ,painter = painterResource(id = R.drawable.profiletemp),
            contentDescription = "ReceiveProfile")
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier
            .width(290.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 0.dp))
            .background(color = appWhite))
                {
                    Text(text = message.message, modifier = Modifier.padding(10.dp), color = appDarkerGray, fontWeight = FontWeight.Bold)
                    Text(
                        text = message.time.toDate().toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, end = 20.dp),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodySmall, color = appDarkerGray
                    )
            }
        }
}

@Composable
fun RightChat(message: Message){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
        Column(modifier = Modifier
            .width(290.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 15.dp))
            .background(color = appBlue))
        {
            Text(text = message.message, modifier = Modifier.padding(10.dp), color = appDarkerGray)
            Text(
                text = message.time.toDate().toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp, end = 20.dp),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall, color = appDarkerGray
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Image(modifier = Modifier
            .height(25.dp)
            .width(25.dp)
            .neu(
                lightShadowColor = appLightGray,
                darkShadowColor = appDarkerGray,
                shadowElevation = 3.dp,
                lightSource = LightSource.LEFT_TOP,
                shape = Flat(RoundedCorner(100.dp))
            )
            .clip(CircleShape)
            ,contentScale = ContentScale.Crop
            ,painter = painterResource(id = R.drawable.profiletemp),
            contentDescription = "ReceiveProfile")
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChatScreenPreview(){
    GlobeGuruTheme {
        ChatScreen(chatId = "Chat123", navBack={})
    }
}