package com.example.globeguru.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.Message
import com.example.globeguru.models.OwnChats
import com.example.globeguru.models.User
import com.example.globeguru.repositories.AuthRepos
import com.example.globeguru.repositories.FireStoreRepo
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repo: FireStoreRepo = FireStoreRepo(),authRepos: AuthRepos = AuthRepos()
): ViewModel() {

    private val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList

    var messageListener: ListenerRegistration? = null
    var chatsListener: ListenerRegistration? = null

    private var currentUser: User? = null
    var currentUserId = ""

    init {
        getCurrentProfile()
    }

    private fun getCurrentProfile()= viewModelScope.launch {
        currentUserId = AuthRepos().getUserId()

        if(currentUserId.isNotBlank()){
            repo.getUserProfile(currentUserId){
                currentUser = it
                Log.d("AA received user info", it.toString())
            }
        }
    }

    fun sendNewMessage(body: String, chatId: String) = viewModelScope.launch {
        if (body.isNotBlank() && chatId.isNotBlank()){
            var sentMessage = Message(
                message = body,
                UserId = currentUser?.username ?: "",
                fromUser = currentUserId,
                UserProfile = currentUser?.profileImage ?: ""
            )
            repo.addNewMessage(
                newMessage = sentMessage,
                ChatId = chatId,
                CurrentUser = currentUserId
            ){
                if(it){
                    Log.d("AAA added success?", it.toString())
                }
            }
        }
    }

    fun realtimeChats(){
        val collectionRef = Firebase.firestore.collection("chatsref")
            .document("Convs").collection("ChatsRefs")
        chatsListener = collectionRef.addSnapshotListener{ snapshot, e ->
            //stop if error
            Log.d("AAA listening", snapshot?.toList().toString())
            if (e != null){
                Log.d("AAA listener went deaf", e.localizedMessage ?: "")
                return@addSnapshotListener
            }

            if(snapshot != null){
                Log.d("AAA received realtime", snapshot.toString())
                _messageList.clear() //first clear everything
                for (document in snapshot){
                    Log.d("AAA received messages", document.toString())
                }

            }
        }
    }


    fun realTimeMessages(chatId: String){
        val collectionRef = Firebase.firestore
            .collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("time", Query.Direction.DESCENDING)
            .limit(50)
        
        messageListener = collectionRef.addSnapshotListener { snapshot, e ->
            //stop if error
            Log.d("AAA listening", snapshot?.toList().toString())
            if (e != null){
                Log.d("AAA listener went deaf", e.localizedMessage ?: "")
                return@addSnapshotListener
            }

            if(snapshot != null){
                Log.d("AAA received realtime", snapshot.toString())
                _messageList.clear() //first clear everything
                for (document in snapshot){
                    _messageList.add(document.toObject(Message::class.java))
                }
                Log.d("AAA received messages", messageList.toString())
            }
        }
    }

    override fun onCleared() {
        Log.d("AAA View", "Closes")
        messageListener?.remove()

        messageListener = null
    }

}

data class ChatUiState(
    val isLoading: Boolean = false,

    val loginEmail: String = "",
)