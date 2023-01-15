package com.groupe5.steamfav.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide.*
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.data.GamesRepository
import com.groupe5.steamfav.databinding.FragmentHomeBinding
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.network.services.SteamWorksWebNetwork
import com.groupe5.steamfav.ui.adapter.GamesAdapter
import com.groupe5.steamfav.ui.models.GameItem
import com.groupe5.steamfav.utils.Resource
import com.groupe5.steamfav.viewmodels.HomeViewModel
import com.groupe5.steamfav.viewmodels.factory.HomeViewModelFactory


class HomeFragment : Fragment(), ItemClickListener<GameItem> {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            GamesRepository(
                SteamWorksWebNetwork(),
                SteamStoreNetwork()
            )
        )
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var statusOperation: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val gamesRecyclerView = binding.mostPlayedGameList
        val adapter = GamesAdapter(this)
        gamesRecyclerView.setItemViewCacheSize(46)
        gamesRecyclerView.addItemDecoration(com.groupe5.steamfav.utils.DividerItemDecoration(20))
        viewModel.spotLightGame.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.let { data ->
                        binding.spotlightGame.gameDescription.text = data.shortDescription
                        binding.spotlightGame.gameTitle.text = data.name
                        with(this)
                            .load(data.backgroundImage)
                           .into(object:CustomTarget<Drawable>(binding.spotlightGame.spotlightGameContainer.width,binding.spotlightGame.spotlightGameContainer.height){
                               override fun onResourceReady(
                                   resource: Drawable,
                                   transition: Transition<in Drawable>?
                               ) {
                                   binding.spotlightGame.spotlightGameContainer.background=resource
                               }

                               override fun onLoadCleared(placeholder: Drawable?) {
                                   binding.spotlightGame.spotlightGameContainer.background=null
                               }

                           })
                        with(this)
                            .load(data.headerImage)
                            .into(binding.spotlightGame.gameCover)
                    }
                }
                Resource.Status.ERROR -> statusOperation?.text =
                    getString(R.string.generic_loading_error)
                Resource.Status.LOADING -> {
                    statusOperation?.visibility = View.VISIBLE
                    statusOperation?.text = getString(R.string.loading_text)
                }
            }

        }
        viewModel.games.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    statusOperation?.visibility = View.GONE
                    response.data?.let { data ->
                        adapter.submitList(data.map {
                            GameItem(
                                it.name,
                                it.publisher,
                                it.priceOverview?.finalFormatted ?: "",
                                it.headerImage,
                                it.backgroundImage
                            )
                        })
                        gamesRecyclerView.run {
                            this.adapter = adapter
                        }

                    }

                }
                Resource.Status.ERROR -> statusOperation?.text =
                    "Some error happened when fetching data"
                Resource.Status.LOADING -> {
                    statusOperation?.visibility = View.VISIBLE
                    statusOperation?.text = "Loading..."
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusOperation = requireActivity().findViewById(R.id.status_operation)
        val mConstraintLayout =
            requireActivity().findViewById(R.id.activity_layout) as ConstraintLayout
        val set = ConstraintSet()

        set.clone(mConstraintLayout)
        set.connect(
            statusOperation!!.id, ConstraintSet.TOP,
            binding.listTitle.id,
            ConstraintSet.BOTTOM, 53
        )
        set.applyTo(mConstraintLayout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: GameItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToGameDetails()
        findNavController().navigate(action)
    }

}