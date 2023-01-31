package com.groupe5.steamfav.network.models

import com.groupe5.steamfav.network.response.PriceOverview

data class GameDetails(
    val id: Long,
    val name: String,
    val type: String,
    val description: String,
    val shortDescription: String,
    val headerImage: String,
    val backgroundImage: String,
    val backgroundRaw:String,
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
        if (type != other.type) return false
        if (description != other.description) return false
        if (shortDescription != other.shortDescription) return false
        if (priceOverview != other.priceOverview) return false
        if (publisher != other.publisher) return false

        return true
    }


}