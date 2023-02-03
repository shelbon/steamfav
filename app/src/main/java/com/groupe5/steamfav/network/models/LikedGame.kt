package com.groupe5.steamfav.network.models

data class LikedGame(val appid:Long=0L,val name:String="",val users:List<String> = emptyList())
