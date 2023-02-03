package com.groupe5.steamfav.data.abstraction
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthRepository : AuthCheckState{
    fun signin(username: String, password: String): Flow<Response<Boolean>
    suspend fun  loginFakeUser(): Flow<NetworkResult<Boolean>>

}