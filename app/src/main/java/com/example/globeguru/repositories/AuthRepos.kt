package com.example.globeguru.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

//Repository for all auth
class AuthRepos {

    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    //Sign Up functionality
    suspend fun registerNewUser(
        email: String,
        password: String,
        createdUser:(String)-> Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("Registed User", it.result.user?.uid.toString())
                    it.result.user?.uid?.let { it1 -> createdUser.invoke(it1) }
                } else {
                    Log.d("Error message", it.exception?.localizedMessage.toString())
                    createdUser.invoke("")
                }
            }.await()
    }

    //Sign In functionality
    suspend fun loginUser(
        email: String,
        password: String,
        isComplete:(Boolean)-> Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("Login User", "Success")
                    isComplete.invoke(true)
                } else {
                    Log.d("Error message", it.exception?.localizedMessage.toString())
                    isComplete.invoke(false)
                }
            }.await()
    }

    fun signOut(){
        Firebase.auth.signOut()
    }
}