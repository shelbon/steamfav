package com.groupe5.steamfav.ui.fragments

import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.groupe5.steamfav.R
import com.groupe5.steamfav.databinding.FragmentGameDetailsBinding
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.ui.adapter.GameDetailsViewPagerAdapter
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.utils.setupActionBarFromFragment
import com.groupe5.steamfav.utils.toggle
import com.groupe5.steamfav.viewmodels.AuthViewModel
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel
import com.groupe5.steamfav.viewmodels.UserDataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.properties.Delegates

class GameDetailsFragment : Fragment(), MenuProvider {


    private val gameDetailsViewModel: GameDetailsViewModel by viewModel()
    private val userDataViewModel: UserDataViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()
    private var _binding: FragmentGameDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var gameDetails: GameDetails
    private var userHasLikedGame by Delegates.observable(false) { prop, old, new ->

        Timber.d("userHasLikedGame: $new")
        if (old != new) {
            this@GameDetailsFragment
                .requireActivity()
                .invalidateOptionsMenu()
        }
    };
    private var userHasWishlisted by Delegates.observable(false) { prop, old, new ->

        Timber.d("userHasWishlisted a game: $new")
        if (old != new) {
            this@GameDetailsFragment
                .requireActivity()
                .invalidateOptionsMenu()
        }
    };
    private val navController by lazy {
        findNavController()
    }
    private val stateListDrawable by lazy {
        ContextCompat.getDrawable(
            this.requireContext(),
            R.drawable.ic_like_state
        ) as StateListDrawable
    }
    private val iconWishlistStateDrawable by lazy {
        ContextCompat.getDrawable(
            this.requireContext(),
            R.drawable.ic_wishlist_state
        ) as StateListDrawable
    }

    private lateinit var menu: Menu
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        appBarConfiguration = AppBarConfiguration(setOf(R.navigation.search, R.navigation.main))
        if (!authViewModel.isConnected) {
            Toast.makeText(requireContext(), "user not logged in", Toast.LENGTH_SHORT).show()
        }
        (requireActivity() as AppCompatActivity).setupActionBarFromFragment(
            binding.toolbar,
            navController,
            appBarConfiguration
        )
        binding.viewPager.adapter = GameDetailsViewPagerAdapter(this)
        val tabTitles = listOf(
            getString(R.string.game_detail_tab_description),
            getString(R.string.game_detail_tab_reviews)
        )
        TabLayoutMediator(binding.gameDetailsTabs, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userDataViewModel.currentUserHasLikedGame.observe(viewLifecycleOwner) { userHasLikedGame ->
            this.userHasLikedGame = userHasLikedGame
        }
        val menuHost: MenuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        gameDetailsViewModel.game().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                NetworkResult.Status.SUCCESS -> {
                    binding.loader.visibility = View.GONE
                    binding.groupGameDetails.visibility = View.VISIBLE
                    with(response.data) {
                        gameDetails = this!!
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

                        gameDetailsViewModel.setDescription(this?.description ?: "")

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


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

        menuInflater.inflate(R.menu.main, menu)
        userHasLikedGame.let {
            if (menu.size() > 0) {
                val menuItem = menu.getItem(0)
                if (it) {
                    menuItem.isChecked = false
                } else {
                    menuItem.isChecked = true
                }
                menuItem.toggle(stateListDrawable)
            }
        }
        userHasWishlisted.let {
            if (menu.size() > 0) {
                val menuItem = menu.getItem(1)
                if (it) {
                    menuItem.isChecked = false
                } else {
                    menuItem.isChecked = true
                }
                menuItem.toggle(iconWishlistStateDrawable)
            }

        }

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.liked_games_fragment -> {
                if (userHasLikedGame) {
                    userDataViewModel.removeLikedGameForUser()
                        .observe(viewLifecycleOwner) { result ->
                            if (result.status == NetworkResult.Status.SUCCESS) {
                                if (result.data!! > 0) {
                                    Snackbar.make(
                                        binding.viewPager,
                                        getString(R.string.game_removed_to_liked),
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                    userHasLikedGame = false
                                    userDataViewModel.refreshData()
                                }
                            } else if (result.status == NetworkResult.Status.ERROR) {
                                Snackbar.make(
                                    binding.viewPager,
                                    result.message.toString(),
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                            }

                        }
                } else {
                    userDataViewModel.addLikedGameForUser(gameDetails.name)
                        .observe(viewLifecycleOwner) {
                            if (it.status == NetworkResult.Status.SUCCESS) {
                                if (it.data!!) {
                                    Snackbar.make(
                                        binding.viewPager,
                                        getString(R.string.game_added_to_liked),
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                    userHasLikedGame = true
                                    userDataViewModel.refreshData()
                                }
                            }
                        }
                }


            }
            R.id.whishlist_fragment -> {
                if (userHasWishlisted) {
                    userDataViewModel.removeUserFromWhishlistedGame()
                        .observe(viewLifecycleOwner) { result ->
                            if (result.status == NetworkResult.Status.SUCCESS) {
                                if (result.data!! > 0) {
                                    Snackbar.make(
                                        binding.viewPager,
                                        getString(R.string.game_removed_from_wishlist),
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                    userHasWishlisted = false
                                    userDataViewModel.refreshWishListed()
                                }
                            } else if (result.status == NetworkResult.Status.ERROR) {
                                Snackbar.make(
                                    binding.viewPager,
                                    result.message.toString(),
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()

                            }

                        }
                } else {
                    userDataViewModel.addGameToUserWishlist(gameDetails.name)
                        .observe(viewLifecycleOwner) {
                            if (it.status == NetworkResult.Status.SUCCESS) {
                                if (it.data!!) {
                                    Snackbar.make(
                                        binding.viewPager,
                                        getString(R.string.game_added_to_wishlist),
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                    userHasWishlisted = true
                                    userDataViewModel.refreshWishListed()
                                }
                            } else {
                                Snackbar.make(
                                    binding.viewPager,
                                    getString(R.string.game_not_added_to_wishlist),
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()

                            }
                        }
                }


            }
        }
        return false
    }


}