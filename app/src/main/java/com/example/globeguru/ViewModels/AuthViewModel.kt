package com.example.globeguru.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globeguru.repositories.AuthRepos
import com.example.globeguru.repositories.FireStoreRepo
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepos: AuthRepos = AuthRepos(),
): ViewModel() {

    val currentUser = authRepos.currentUser
    val hashUser: Boolean
        get() = authRepos.hasUser()

    var authUiState by mutableStateOf(AuthUiState())
    private set

    //fun to handle update states
    fun handleStateChanges(target: String, value: String){
        if(target == "loginEmail"){
            authUiState = authUiState.copy(loginEmail = value)
        } else if(target == "loginPassword"){
            authUiState = authUiState.copy(loginPassword = value)
        } else if(target == "registerUsername"){
            authUiState = authUiState.copy(registerUsername = value)
        } else if(target == "registerPassword"){
            authUiState = authUiState.copy(registerPassword = value)
        } else if(target == "registerConPassword"){
            authUiState = authUiState.copy(registerConPassword = value)
        } else if(target == "registerEmail"){
            authUiState = authUiState.copy(registerEmail = value)
        } else if(target == "registerCity"){
            authUiState = authUiState.copy(registerCity = value)
        } else if(target == "registerTraveler"){
            authUiState = authUiState.copy(registerTraveler = value)
        }
    }
    fun createNewUser(context: Context)= viewModelScope.launch {
        authUiState = authUiState.copy(errorMessage = "")
        try {
            if(authUiState.registerUsername.isBlank() || authUiState.registerEmail.isBlank() || authUiState.registerPassword.isBlank()){
                authUiState = authUiState.copy(errorMessage = "Please fill in all the fields")
            } else {
                if (authUiState.registerPassword === authUiState.registerConPassword){
                    authUiState = authUiState.copy(isLoading = true) //start loading

                    //call auth
                    authRepos.registerNewUser(
                        authUiState.registerEmail,
                        authUiState.registerPassword
                    ) {
                            userId -> if(userId.isNotBlank()){
                        FireStoreRepo().createUserInDb(uid = userId, username = authUiState.registerUsername, email = authUiState.registerEmail, traveller = authUiState.registerTraveler.toBoolean()){
                            if(it){
                                Log.d("Register Success: ", userId)

                                Toast.makeText(context, "Registration Completed", Toast.LENGTH_SHORT).show()

                                authUiState = authUiState.copy(authSuccess = true)
                            } else {
                                Log.d("Something went wrong: ", userId)

                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()

                                authUiState = authUiState.copy(authSuccess = false)
                            }
                        }

                    } else {
                        //register failed
                        Log.d("Register failed: ", "Something went wrong")
                        Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
                        authUiState = authUiState.copy(authSuccess = false)
                        authUiState = authUiState.copy(errorMessage = "Invalid credentials")
                    }
                    }
                } else {
                    authUiState = authUiState.copy(errorMessage = "Passwords don't match")
                }
            }
        } catch (e:Exception){
            Log.d("Error registering: ", e.localizedMessage)
            e.printStackTrace()
        } finally {
            authUiState = authUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context)= viewModelScope.launch {
        authUiState = authUiState.copy(errorMessage = "")
        try {
            if(authUiState.loginEmail.isBlank() || authUiState.loginPassword.isBlank()){
                authUiState = authUiState.copy(errorMessage = "Please fill in all the fields")
            } else {
                authUiState = authUiState.copy(isLoading = true) //start loading

                //call auth
                //pass variables
                authRepos.loginUser(
                    authUiState.loginEmail,
                    authUiState.loginPassword
                ) { isCompleted -> if(isCompleted){
                    //we get a userId
                    Log.d("Login success: ", "")

                    Toast.makeText(context, "login Completed", Toast.LENGTH_SHORT).show()

                    authUiState = authUiState.copy(authSuccess = true)
                } else {
                    //register failed
                    Log.d("AAA Login failed: ", "Something went wrong")
                    Toast.makeText(context, "login Failed", Toast.LENGTH_SHORT).show()
                    authUiState = authUiState.copy(authSuccess = false)
                    authUiState = authUiState.copy(errorMessage = "Invalid credentials")
                }
                }
            }
        } catch (e:Exception){
            Log.d("Error Logging in: ", e.localizedMessage)
            e.printStackTrace()
        } finally {
            authUiState = authUiState.copy(isLoading = false)
        }
    }
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authSuccess: Boolean = false,

    //state values for login
    val loginEmail: String = "",
    val loginPassword: String = "",

    //state values for register
    val registerUsername: String = "",
    val registerPassword: String = "",
    val registerConPassword: String = "",
    val registerEmail: String = "",
    val registerCity: String = "",
    val registerTraveler: String = "false",
)