package com.example.globeguru.ViewModels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globeguru.models.User
import com.example.globeguru.repositories.AuthRepos
import com.example.globeguru.repositories.FireStoreRepo
import com.example.globeguru.repositories.StorageRepo
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepos: AuthRepos = AuthRepos(),
    private val fireStoreRepo: FireStoreRepo = FireStoreRepo(),
    private val storeRepo: StorageRepo = StorageRepo()
): ViewModel() {

    private val hasUser = authRepos.hasUser()

    private val currentUserId = authRepos.getUserId()

    var prevImage: String = ""

    init {
        Log.d("AAA Profile view model", "INIT")
        getProfileDate()
    }

    var profileUiState by mutableStateOf(ProfileUiState())
        private set

    fun handleProfileStateChange(target: String, value: String){
        if(target == "userName"){
            profileUiState = profileUiState.copy(userName = value)
        } else if (target == "traveler") {
            profileUiState = profileUiState.copy(traveler = value)
        }
    }


    fun handleProfileImgUpdate(value: Uri){
        profileUiState = profileUiState.copy(profileImage = value)
    }

    private fun getProfileDate()= viewModelScope.launch {
        if(currentUserId.isNotBlank()){

            fireStoreRepo.getUserProfile(currentUserId){
                Log.d("AA Two", profileUiState.cityCode)
                profileUiState = profileUiState.copy(
                    userName = it?.username ?: "",
                    email = it?.email ?: "",
                    city = it?.city ?: "",
                    chats = it?.chats ?: "",
                    cityCode = it?.cityCode ?: "",
                    profileImage = Uri.parse(it?.profileImage),
                    traveler = it?.traveller.toString()
                )
                prevImage = it?.profileImage ?: ""
                Log.d("AA received user info", it.toString())
                it?.cityCode?.let { it1 -> Log.d("AA One", it1) }
            }
        }
    }
    fun saveProfileData() = viewModelScope.launch {
        if (hasUser){
            var downloadUrl = prevImage
            if(prevImage != profileUiState.profileImage.toString() || prevImage.isBlank()){
               storeRepo.uploadImg(imageUri = profileUiState.profileImage, fileName = "$currentUserId-${profileUiState.userName}"){
                   downloadUrl = it
               }
            }
            Log.d("AAA url", downloadUrl)
            fireStoreRepo.updateProfile(user = User(id = currentUserId,
                username = profileUiState.userName,
                email = profileUiState.email,
                traveller = profileUiState.traveler.toBoolean(),
                cityCode = profileUiState.cityCode,
                city = profileUiState.city,
                chats = profileUiState.chats,
                profileImage = downloadUrl)){
//                    Log.d("AAA updated user", it.toString())
            }
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authSuccess: Boolean = false,

    //state values for register
    val userName: String = "",
    val email: String = "",
    val city: String = "",
    val cityCode: String = "",
    val profileImage: Uri = Uri.EMPTY,
    val traveler: String = "false",
    val chats: String = ""
)