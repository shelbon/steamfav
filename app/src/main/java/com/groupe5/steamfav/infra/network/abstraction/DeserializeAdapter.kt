package com.groupe5.steamfav.infra.network.abstraction

import com.groupe5.steamfav.infra.network.models.GameDetails

interface DeserializeAdapter<T, U> : Adapter {

    fun from(obj: T): GameDetails?
}