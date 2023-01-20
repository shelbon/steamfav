package com.groupe5.steamfav.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.databinding.GameItemBinding
import com.groupe5.steamfav.ui.models.GameItem
import com.groupe5.steamfav.utils.applyBoldEndingAtDelimiter
import com.groupe5.steamfav.utils.applyUnderlineEndingAtDelimiter

class GamesAdapter(private val itemClickListener: ItemClickListener<GameItem>) :
    ListAdapter<GameItem, GamesAdapter.GameViewHolder>(
        DiffCallback()
    ) {
    inner class GameViewHolder(var itemBinding: GameItemBinding) :
        RecyclerView.ViewHolder(
            itemBinding.root
        ) {
        fun setData(gameItem: GameItem) {
            val priceStringBuilder = StringBuilder();
            priceStringBuilder.append(itemBinding.root.context.getString(R.string.price));
            priceStringBuilder.append(":")
            priceStringBuilder.append(gameItem.price);
            with(itemBinding) {
                gameTitle.text = gameItem.name
                gamePrice.apply{
                    text = priceStringBuilder.toString()
                    applyBoldEndingAtDelimiter()
                    applyUnderlineEndingAtDelimiter()
                }

                gameEditors.text = gameItem.publishers.joinToString(",\n")
                Glide.with(itemBinding.root)
                    .load(gameItem.backgroundImage)

                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            itemBinding.gameItemLayout.background = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            itemBinding.gameItemLayout.background = placeholder
                        }

                    });
                Glide.with(itemBinding.root.context)
                    .load(gameItem.image)
                    .into(gameImage)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<GameItem>() {
        override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = GameItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return GameViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.setData(game)
        holder.itemBinding.btnReadMore.setOnClickListener()
        {
            itemClickListener.onItemClick(game)
        }
    }

}
