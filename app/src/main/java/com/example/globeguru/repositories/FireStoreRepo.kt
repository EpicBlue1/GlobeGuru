package com.example.globeguru.repositories

import android.content.ContentValues
import android.util.Log
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.Message
import com.example.globeguru.models.User
import com.google.firebase.firestore.SetOptions
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

    fun createUserInDb(uid: String, traveller: Boolean, city:String, username: String, email: String, cityCode: String, onSuccess: (Boolean)-> Unit){
        userRef.document(uid)
            .set(User(id= uid, username = username, city = city, cityCode = cityCode, email = email, profileImage = "", traveller = traveller))
            .addOnSuccessListener {
                onSuccess.invoke(true)
                Log.d("AAA Login Success: ", it.toString())
            }
            .addOnFailureListener{
                onSuccess.invoke(false)
                it.localizedMessage?.let { it1 -> Log.d("AAA Login failure: ", it1) }
            }
    }

    suspend fun getAllChats(onSuccess: (List<User>?)-> Unit){

        val conversation = arrayListOf<User>()

        userRef.orderBy("username").get()
            .addOnSuccessListener {
                for(document in it){
                    conversation.add(
                        User(
                            id = document.id,
                             username = document.data["username"].toString(),
                             email = document.data["email"].toString(),
                             profileImage = document.data["profileImage"].toString(),
                             city = document.data["city"].toString(),
                             cityCode = document.data["cityCode"].toString(),
                             traveller = document.data["traveller"] as Boolean
                        )
                    )
                }
                Log.d("AAA Chats Success: ", conversation.toString());
                onSuccess(conversation)
            }
            .addOnFailureListener{
                onSuccess(null)
                it.localizedMessage?.let { it1 -> Log.d("AAA chats failure: ", it1) }
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
                it.localizedMessage?.let { it1 ->
                    Log.d("AAA there was a problem adding message",
                        it1
                    )
                }
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
                    onSuccess.invoke(it.toObject(User::class.java))
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

    suspend fun updateProfile(user: User, onSuccess: (Boolean)-> Unit){
        userRef.document(user.id)
                //just merge don't override
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess.invoke(true)
                Log.d("AAA update Success: ", it.toString())
            }
            .addOnFailureListener{
                onSuccess.invoke(false)
                it.localizedMessage?.let { it1 -> Log.d("AAA update failure: ", it1) }
                    it.printStackTrace()
            }.await()
    }
}
