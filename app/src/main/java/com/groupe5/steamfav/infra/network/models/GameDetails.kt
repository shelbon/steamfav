package com.groupe5.steamfav.infra.network.models

import com.groupe5.steamfav.infra.network.response.PriceOverview

data class GameDetails(
    val name: String,
    val description: String,
    val priceOverview: PriceOverview?,
    val publisher: List<String>
) {


    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (priceOverview?.hashCode() ?: 0)
        result = 31 * result + publisher.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameDetails

        if (name != other.name) return false
        if (description != other.description) return false
        if (priceOverview != other.priceOverview) return false
        if (publisher != other.publisher) return false

        return true
    }
}