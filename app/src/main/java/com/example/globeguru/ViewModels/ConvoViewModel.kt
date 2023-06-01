package com.example.globeguru.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globeguru.models.Conversations
import com.example.globeguru.repositories.FireStoreRepo
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ConvoViewModel(
    private val repo: FireStoreRepo = FireStoreRepo()
):ViewModel() {

    private val _chatList = mutableStateListOf<Conversations>()
    val chatList: List<Conversations> = _chatList

    //get convos on initialization
    init{
        getChats()
    }

    fun getChats() = viewModelScope.launch{
        repo.getAllChats() {
            data-> if(data != null){
                for(document in data){
                    _chatList.add(document)
                }
            }
        }
    }
}