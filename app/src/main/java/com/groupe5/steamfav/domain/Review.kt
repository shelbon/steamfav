package com.groupe5.steamfav.domain

data class Review(val recommendationId: Long, val author: Author, val rating: Int, val body: String)

data class Author(val id:Long, val username: String)
