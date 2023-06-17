package com.example.globeguru.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageRepo {
    val storageRef = Firebase.storage.reference

    suspend fun uploadImg(
        imageUri: Uri,
        fileName: String,
        onSuccess: (downloadUrl: String) -> Unit
    ){
        try {
            val downloadUrl = storageRef.child("profiles/${fileName}")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()

            Log.d("AA Upload Success", downloadUrl.toString())
            onSuccess(downloadUrl.toString())

        } catch (e: Exception){
            Log.d("AA Something went wrong Upload", e.localizedMessage)
            e.printStackTrace()
            onSuccess("")
        }
    }
}