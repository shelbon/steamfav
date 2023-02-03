package com.groupe5.steamfav.network.response.adapter

import com.groupe5.steamfav.network.abstraction.DeserializeAdapter
import com.groupe5.steamfav.network.models.UserInfo

import com.groupe5.steamfav.network.response.UserInfoResponse
import com.squareup.moshi.FromJson

class UserInfoAdapter: DeserializeAdapter<UserInfoResponse,List<UserInfo>> {
    @FromJson
    override fun from(obj: UserInfoResponse): List<UserInfo> {
         return obj.map {
             userInfo ->
             with(userInfo){
                 UserInfo(
                     steamId.toLong(),
                     personaName
                 )
             }

         }
    }
}