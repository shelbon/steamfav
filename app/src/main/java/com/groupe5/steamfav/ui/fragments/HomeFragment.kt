package com.groupe5.steamfav.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.auth.FirebaseAuth
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.databinding.FragmentHomeBinding
import com.groupe5.steamfav.ui.adapter.GamesAdapter
import com.groupe5.steamfav.ui.models.GameItem
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.viewmodels.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), ItemClickListener<GameItem> {



    private val viewModel:HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val authProvider:FirebaseAuth by  inject()
    private val binding get() = _binding!!
    private val navController by lazy {
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(authProvider.currentUser==null){
            navController.navigate(R.id.login_fragment)
        }
        val gamesRecyclerView = binding.mostPlayedGameList
        val adapter = GamesAdapter(this)
        gamesRecyclerView.adapter = adapter
        gamesRecyclerView.setItemViewCacheSize(50)
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(searchQuery: String): Boolean {
                return true
            }
            override fun onQueryTextSubmit(searchQuery: String): Boolean {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchView(searchQuery)
                findNavController().navigate(action)
                return true
            }
        })
        viewModel.spotLightGame.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                NetworkResult.Status.SUCCESS -> {
                    binding.spotlightGame.group.visibility = View.VISIBLE
                    binding.spotlightGame.networkStatusSpotlightGame.visibility = View.GONE
                    response.data?.let { data ->
                        binding.spotlightGame.btnReadMore.setOnClickListener {
                            onItemClick(
                                GameItem(
                                    data.id,
                                    data.name,
                                    data.publisher,
                                    data.priceOverview?.finalFormatted
                                        ?: getString(R.string.freeText),
                                    data.headerImage,
                                    data.backgroundImage
                                )
                            )
                        }
                        binding.spotlightGame.gameDescription.setTextFuture(
                            PrecomputedTextCompat.getTextFuture(
                                HtmlCompat.fromHtml(data.shortDescription,0),
                                TextViewCompat.getTextMetricsParams(binding.spotlightGame.gameDescription),
                                null
                            )
                        )
                        binding.spotlightGame.gameTitle.text = data.name
                        Glide.with(this)
                            .asDrawable()
                            .load(data.backgroundImage)
                            .into(object : CustomTarget<Drawable>(
                                binding.spotlightGame.spotlightGameContainer.width,
                                binding.spotlightGame.spotlightGameContainer.height
                            ) {
                                override fun onResourceReady(
                                    resource: Drawable,
                                    transition: Transition<in Drawable>?
                                ) {
                                    binding.spotlightGame.spotlightGameContainer.background =
                                        resource
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                }

                            })
                        Glide.with(this)
                            .load(data.headerImage)
                            .into(binding.spotlightGame.gameCover)
                    }

                }
                NetworkResult.Status.ERROR -> binding.statusOperation.text = response.message
                NetworkResult.Status.LOADING -> {
                    binding.spotlightGame.group.visibility = View.GONE
                    binding.spotlightGame.networkStatusSpotlightGame.visibility = View.VISIBLE
                    binding.spotlightGame.networkStatusSpotlightGame.text =
                        getString(R.string.loading_text)
                }
            }

        }
        viewModel.games.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                NetworkResult.Status.SUCCESS -> {
                    binding.statusOperation.visibility = View.GONE
                    response.data?.let { data ->
                        adapter.submitList(data.map {
                            GameItem(
                                it.id,
                                it.name,
                                it.publisher,
                                it.priceOverview?.finalFormatted ?: getString(R.string.freeText),
                                it.headerImage,
                                it.backgroundImage
                            )
                        })


                    }

                }
                NetworkResult.Status.ERROR -> binding.statusOperation.text = response.message
                NetworkResult.Status.LOADING -> {
                    binding.statusOperation.visibility = View.VISIBLE
                    binding.statusOperation.text = getString(R.string.loading_text)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: GameItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(item.id)
        findNavController().navigate(action)
    }



}