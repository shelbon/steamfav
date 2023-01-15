package com.groupe5.steamfav.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupe5.steamfav.R
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel

class GameDetails : Fragment() {

    companion object {
        fun newInstance() = GameDetails()
    }

    private lateinit var viewModel: GameDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[GameDetailsViewModel::class.java]
        // TODO: Use the ViewModel
    }

}