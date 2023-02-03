package com.groupe5.steamfav.data.abstraction

import kotlinx.coroutines.flow.Flow

interface AuthCheckState {
    fun check(): Flow<Boolean>
    fun isUserAuthenticatedInFirebase():Boolean
}