package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.groupe5.steamfav.R
import com.groupe5.steamfav.data.GameReviewsRepository
import com.groupe5.steamfav.data.GamesRepository
import com.groupe5.steamfav.databinding.FragmentGameDetailsBinding
import com.groupe5.steamfav.network.services.SteamCommunityNetwork
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.network.services.SteamWorksWebNetwork
import com.groupe5.steamfav.ui.adapter.GameDetailsViewPagerAdapter
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailsFragment : Fragment() {


    private val viewModel: GameDetailsViewModel by viewModel()
    private var _binding: FragmentGameDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        appBarConfiguration = AppBarConfiguration(setOf(R.navigation.search, R.navigation.main))
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        binding.viewPager.adapter = GameDetailsViewPagerAdapter(this)
        val tabTitles= listOf(getString(R.string.game_detail_tab_description),getString(R.string.game_detail_tab_reviews))
        TabLayoutMediator(binding.gameDetailsTabs, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.game().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                NetworkResult.Status.SUCCESS -> {
                    binding.loader.visibility = View.GONE
                    binding.groupGameDetails.visibility = View.VISIBLE
                    with(response.data) {
                        Glide.with(this@GameDetailsFragment)
                            .load(this?.backgroundRaw)
                            .centerCrop()
                            .into(binding.gameFrontImage)
                        Glide.with(this@GameDetailsFragment)
                            .load(this?.headerImage)
                            .into(binding.gameDetailsCard.gameCover)
                        binding.gameDetailsCard.gameTitle.text = this?.name ?: ""
                        binding.gameDetailsCard.gamePublishers.text =
                            this?.publisher?.joinToString(",\n") ?: ""

                        viewModel.setDescription(this?.description?:"")

                    }
                }
                NetworkResult.Status.ERROR -> {
                    binding.groupGameDetails.visibility = View.GONE
                    binding.gameDetailsStatusInformation.visibility = View.VISIBLE
                    binding.gameDetailsStatusInformation.text = response.message

                }
                NetworkResult.Status.LOADING -> {
                    binding.loader.visibility = View.VISIBLE
                    binding.groupGameDetails.visibility = View.GONE
                }

            }

        }
    }


}