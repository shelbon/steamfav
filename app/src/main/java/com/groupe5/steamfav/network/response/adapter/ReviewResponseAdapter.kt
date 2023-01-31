package com.groupe5.steamfav.network.response.adapter

import com.groupe5.steamfav.domain.Author
import com.groupe5.steamfav.domain.Review
import com.groupe5.steamfav.network.abstraction.DeserializeAdapter
import com.groupe5.steamfav.network.response.ReviewResponse
import com.squareup.moshi.FromJson
import kotlin.random.Random

class ReviewResponseAdapter: DeserializeAdapter<ReviewResponse, List<Review>> {
    @FromJson
    override fun from(obj: ReviewResponse): List<Review> {
          if(obj.success==1L){
              return obj.reviews.map {
                  review ->
                  Review(
                      review.recommendationid.toLong(),
                      Author(
                          review.author.steamid.toLong(),
                          ""
                      ),
                      Random.nextInt(5),
                      review.review
                  )
              }
          }
         return emptyList()
    }

}