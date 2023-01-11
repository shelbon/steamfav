package com.groupe5.steamfav.network.abstraction

interface DeserializeAdapter<F, T> : Adapter {

    fun from(obj: F): T?
}