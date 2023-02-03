package com.groupe5.steamfav.data.abstraction

import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository:AuthCheckState {
    suspend fun  loginFakeUser(): Flow<NetworkResult<Boolean>>
}