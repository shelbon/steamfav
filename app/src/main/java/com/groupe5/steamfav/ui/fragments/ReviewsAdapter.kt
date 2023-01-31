package com.groupe5.steamfav.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.groupe5.steamfav.databinding.ReviewItemBinding
import com.groupe5.steamfav.domain.Review


class ReviewsAdapter() : ListAdapter<Review, ReviewsAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapter.ViewHolder {

        return ViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item)
    }


    inner class ViewHolder(private val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(reviewData: Review) {
            with(binding) {
                with(reviewData) {
                    username.text = author.username
                    review.text = body
                    reviewRating.rating=rating.toFloat()
                }
            }

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem.recommendationId == newItem.recommendationId


        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem == newItem


    }

}