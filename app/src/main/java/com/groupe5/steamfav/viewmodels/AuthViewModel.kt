package com.groupe5.steamfav.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe5.steamfav.data.abstraction.AuthRepository
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository):ViewModel() {
    val isConnected = authRepository.isUserAuthenticatedInFirebase()
    private val _signInState = MutableStateFlow<NetworkResult<Boolean>>(NetworkResult.Success(false))
    val signInState: StateFlow<NetworkResult<Boolean>> = _signInState
    fun connectFakeUser(){
        viewModelScope.launch {
            authRepository.loginFakeUser().collect{
                _signInState.value=it
            }
        }
    }
}