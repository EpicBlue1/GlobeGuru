package com.example.globeguru.repositories

import android.util.Log
import com.example.globeguru.models.Conversations
import com.example.globeguru.models.User
import com.google.firebase.firestore.ktx.firestore
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
        chatsRef.orderBy("name").get()
            .addOnSuccessListener {
                onSuccess(it.toObjects(Conversations::class.java))
                Log.d("AAA Chats Success: ", it.toString())
            }
            .addOnFailureListener{
                onSuccess(null)
                Log.d("AAA chats failure: ", it.localizedMessage)
            }.await()
    }
}