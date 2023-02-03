package com.groupe5.steamfav.data.impl

import com.google.firebase.auth.FirebaseAuth
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class AuthRepository(private val auth: FirebaseAuth) : com.groupe5.steamfav.data.abstraction.AuthRepository {

    override fun signin(username: String, password: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun check() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null


}