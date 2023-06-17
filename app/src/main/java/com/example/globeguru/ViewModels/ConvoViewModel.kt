package com.example.globeguru.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globeguru.models.Conversations
import com.example.globeguru.repositories.FireStoreRepo
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.globeguru.models.User

class ConvoViewModel(
    private val repo: FireStoreRepo = FireStoreRepo()
):ViewModel() {

    private val _chatList = mutableStateListOf<User>()
    val chatList: List<User> = _chatList

    //get convos on initialization
//    init{
//        getChats()
//    }

    fun getChats(filter:String) = viewModelScope.launch{
        repo.getAllChats() {
            data -> if(data != null){
                for(document in data){
                    if(document.cityCode == filter){
                        _chatList.add(document)
                    }
                }
            Log.d("DDD Got chats", data.toString())
            Log.d("DDD Got chats", filter)
            }
        }
    }
}