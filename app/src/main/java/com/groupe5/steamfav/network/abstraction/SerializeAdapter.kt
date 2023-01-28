package com.groupe5.steamfav.network.abstraction

interface SerializeAdapter<T, U> : Adapter {
    fun to(obj: T): U
}