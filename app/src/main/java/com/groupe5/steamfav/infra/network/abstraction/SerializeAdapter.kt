package com.groupe5.steamfav.infra.network.abstraction

interface SerializeAdapter<T, U> : Adapter {
    fun to(obj: T): U
}