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
import com.example.globeguru.models.OwnChats
import com.example.globeguru.models.User
import com.example.globeguru.repositories.AuthRepos
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ConvoViewModel(
    private val repo: FireStoreRepo = FireStoreRepo(),
    private val authRepos: AuthRepos = AuthRepos(),
):ViewModel() {

    val db = Firebase.firestore

    private val _chatList = mutableStateListOf<User>()
    val chatList: List<User> = _chatList

    private val currentUserId = authRepos.getUserId()

    private val _ownchatList = mutableStateListOf<User>()
    val ownchatList: List<User> = _ownchatList

    fun getChats(filter: String) = viewModelScope.launch {
        repo.getAllChats() { data ->
            if (data != null) {
                for (document in data) {
                    if (document.cityCode == filter && currentUserId != document.id) {
                        _chatList.add(document)
                    }
                }
                Log.d("DDD Got chats", data.toString())
            }
        }
    }

    fun getOwnChats(trav: Boolean, id: String) = viewModelScope.launch {
        val queryField = if (trav) "userRef" else "chatsRef"
        val queryValue = if (trav) currentUserId else id

        val querySnapshot = db.collection("chatsref")
            .document("Convs")
            .collection("ChatsRefs")
            .whereEqualTo(queryField, queryValue)
            .get()
            .await()

        for (document in querySnapshot) {
            val chatsRefId = document.getString("chatsRef")
            val userRefId = document.getString("userRef")

            val conversation = arrayListOf<User>()

            db.collection("users").get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        if (document.id == userRefId && trav) {
                            val id = document.id
                            val username = document.data["username"].toString()
                            val email = document.data["email"].toString()
                            val profileImage = document.data["profileImage"].toString()
                            val city = document.data["city"].toString()
                            val cityCode = document.data["cityCode"].toString()
                            val traveller = document.data["traveller"] as Boolean
                            val chats = document.data["chats"].toString()

                            val user = User(
                                id,
                                username,
                                email,
                                profileImage,
                                city,
                                cityCode,
                                traveller,
                                chats
                            )
                            conversation.add(user)
                            _ownchatList.addAll(conversation)
                        } else if (document.id == chatsRefId && !trav) {
                            val id = document.id
                            val username = document.data["username"].toString()
                            val email = document.data["email"].toString()
                            val profileImage = document.data["profileImage"].toString()
                            val city = document.data["city"].toString()
                            val cityCode = document.data["cityCode"].toString()
                            val traveller = document.data["traveller"] as Boolean
                            val chats = document.data["chats"].toString()

                            val user = User(
                                id,
                                username,
                                email,
                                profileImage,
                                city,
                                cityCode,
                                traveller,
                                chats
                            )
                            conversation.add(user)
                            _ownchatList.addAll(conversation)
                        }
                    }
                }

        }
    }
}
