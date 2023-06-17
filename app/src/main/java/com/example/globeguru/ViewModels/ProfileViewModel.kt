package com.example.globeguru.ViewModels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globeguru.repositories.AuthRepos
import com.example.globeguru.repositories.FireStoreRepo
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val authRepos: AuthRepos = AuthRepos(),
    private val fireStoreRepo: FireStoreRepo = FireStoreRepo()
): ViewModel() {

    private val hasUser = authRepos.hasUser()

    private val currentUserId = authRepos.getUserId()

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
                profileUiState = profileUiState.copy(
                    userName = it?.username ?: "",
                    email = it?.email ?: "",
                    profileImage = Uri.parse(it?.profileImage),
                    traveler = it?.traveller.toString()
                )
                Log.d("AA received user info", it.toString())
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
    val profileImage: Uri = Uri.EMPTY,
    val traveler: String = "false",
)