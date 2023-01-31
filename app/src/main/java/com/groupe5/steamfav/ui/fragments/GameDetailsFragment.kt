package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.groupe5.steamfav.R
import com.groupe5.steamfav.databinding.FragmentGameDetailsBinding
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetails : Fragment() {


    private val viewModel: GameDetailsViewModel by viewModel()
    private val args by navArgs<GameDetailsArgs>()
    private var _binding: FragmentGameDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        binding.gameDetailsText.text = args.gameId.toString()
        appBarConfiguration = AppBarConfiguration(setOf(R.navigation.search,R.navigation.main))
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        return binding.root
    }


}