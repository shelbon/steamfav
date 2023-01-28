package com.groupe5.steamfav.abstraction


interface ItemClickListener<T> {
    fun onItemClick(item: T)
}