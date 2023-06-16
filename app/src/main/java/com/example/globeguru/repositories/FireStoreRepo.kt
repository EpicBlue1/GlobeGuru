package com.example.globeguru.repositories

import android.content.ContentValues
import android.util.Log
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.Message
import com.example.globeguru.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

const val USER_REF = "users"
const val CONV_REF = "chats"

class FireStoreRepo {
    val db = Firebase.firestore

    private val userRef = db.collection(USER_REF)
    private val chatsRef = db.collection(CONV_REF)

    fun createUserInDb(uid: String, traveller: Boolean, username: String, email: String, onSuccess: (Boolean)-> Unit){
        userRef.document(uid)
            .set(User(id= uid, username = username, email = email, profileImage = "", traveller = traveller))
            .addOnSuccessListener {
                onSuccess.invoke(true)
                Log.d("AAA Login Success: ", it.toString())
            }
            .addOnFailureListener{
                onSuccess.invoke(false)
                Log.d("AAA Login failure: ", it.localizedMessage)
            }
    }

    suspend fun getAllChats(onSuccess: (List<Conversations>?)-> Unit){

        val conversation = arrayListOf<Conversations>()

        chatsRef.orderBy("name").get()
            .addOnSuccessListener {
                for(document in it){
                    conversation.add(
                        Conversations(
                            id = document.id,
                            name= document.data["name"].toString(),
                            image=  document.data["image"].toString(),
                            countryImage=  document.data["countryImage"].toString(),
                            countryOrigin= document.data["countryOrigin"].toString(),
                            totalMessages= document.data["totalMessages"].toString(),
                        )
                    )
                }
                Log.d("AAA Chats Success: ", conversation.toString());
                onSuccess(conversation)
            }
            .addOnFailureListener{
                onSuccess(null)
                Log.d("AAA chats failure: ", it.localizedMessage)
            }.await()
    }

    suspend fun addNewMessage(
        newMessage: Message,
        ChatId: String,
        onSuccess: (Boolean) -> Unit
    ) {
        chatsRef.document(ChatId).collection("messages")
            .add(newMessage)
            .addOnSuccessListener {
                Log.d("AAA new message added: ", it.id)
                onSuccess.invoke(true)
            }
            .addOnFailureListener{
                Log.d("AAA there was a problem adding message", it.localizedMessage)
                it.printStackTrace()
                onSuccess.invoke(false)
            }.await()
    }

    suspend fun getUserProfile(uid: String, onSuccess: (User?) -> Unit){
        Log.d("AAA getting user", uid)
        userRef.document(uid).get()
            .addOnSuccessListener {
                if(it != null){
                    Log.d(ContentValues.TAG, "AA DocumentSnapShot data:" )
                    onSuccess.invoke(it?.toObject(User::class.java))
                } else {
                    Log.d(ContentValues.TAG, "AA no such data" )
                    onSuccess.invoke(null)
                }
            }
            .addOnFailureListener{
                Log.d(ContentValues.TAG, "AA get failed with" )
                onSuccess.invoke(null)
            }.await()
    }
}

private fun Any.invoke(toObject: User?) {

}
