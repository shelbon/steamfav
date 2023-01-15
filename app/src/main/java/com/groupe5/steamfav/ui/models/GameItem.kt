package com.groupe5.steamfav.ui.models

data class GameItem(
    val name: String,
    val publishers: List<String> = emptyList(),
    val price: String,
    val image: String,
    val backgroundImage:String
) {
}