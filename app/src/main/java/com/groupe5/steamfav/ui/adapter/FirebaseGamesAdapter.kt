package com.groupe5.steamfav.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.databinding.GameItemBinding
import com.groupe5.steamfav.network.models.LikedGame
import com.groupe5.steamfav.ui.models.GameItem
import com.groupe5.steamfav.utils.applyBoldEndingAtDelimiter
import com.groupe5.steamfav.utils.applyUnderlineEndingAtDelimiter

open class FirebaseGamesAdapter(private val itemClickListener: ItemClickListener<LikedGame>,
                                private var likedGames: List<LikedGame>,
                                private val options:FirestoreRecyclerOptions<LikedGame>) :
    FirestoreRecyclerAdapter<LikedGame, FirebaseGamesAdapter.GameViewHolder>(
options
    ) {
    inner class GameViewHolder(var itemBinding: GameItemBinding) :
        RecyclerView.ViewHolder(
            itemBinding.root
        ) {
        fun setData(gameItem: LikedGame) {
//            val priceStringBuilder = StringBuilder()
//            priceStringBuilder.append(itemBinding.root.context.getString(R.string.price))
//            priceStringBuilder.append(":")
//            priceStringBuilder.append(gameItem.price)
            with(itemBinding) {
                gameTitle.text = gameItem.name
//                gamePrice.apply{
//                    text = priceStringBuilder.toString()
//                    applyBoldEndingAtDelimiter()
//                    applyUnderlineEndingAtDelimiter()
//                }
//
//                gameEditors.text = gameItem.publishers.joinToString(",\n")
//                Glide.with(itemBinding.root).asDrawable()
//                    .load(gameItem.backgroundImage)
//
//                    .into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            itemBinding.gameItemLayout.background = resource
//
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                            itemBinding.gameItemLayout.background = placeholder
//                        }
//
//                    });
//                Glide.with(itemBinding.root.context)
//                    .load(gameItem.image)
//                    .into(gameImage)
            }
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
    fun updateList(list: List<LikedGame> ) {
        this.likedGames = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: GameViewHolder, position: Int, model: LikedGame) {
        val likedGame=snapshots[position]
        holder.setData(likedGame)
        holder.itemBinding.btnReadMore.setOnClickListener()
        {
            itemClickListener.onItemClick(likedGame)
        }
    }



}
