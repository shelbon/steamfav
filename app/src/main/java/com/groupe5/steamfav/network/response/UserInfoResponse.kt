package com.groupe5.steamfav.network.response

import com.squareup.moshi.Json

typealias UserInfoResponse=List<UserInfo>


data class UserInfo(
    @Json(name="steamid")
    val steamId: String,
    @Json(name="accountid")
    val accountId: Long,

    @Json(name = "persona_name")
    val personaName: String,

    @Json(name = "avatar_url")
    val avatarURL: String?="",

    @Json(name = "profile_url")
    val profileURL: String?="",

    @Json(name = "persona_state")
    val personaState: Long,
    @Json(name = "city")
    val city: String?="",
    @Json(name = "state")
    val state: String?="",
    @Json(name = "country")
    val country: String?="",

    @Json(name = "real_name")
    val realName: String?=""

)
