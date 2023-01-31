package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.databinding.FragmentSearchBinding
import com.groupe5.steamfav.ui.adapter.GamesAdapter
import com.groupe5.steamfav.ui.models.GameItem
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.utils.onlyLetters
import com.groupe5.steamfav.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(), ItemClickListener<GameItem> {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SearchFragmentArgs>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy {
        findNavController()
    }
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        appBarConfiguration = AppBarConfiguration(setOf(R.navigation.main, R.navigation.search))
        setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var searchQuery = args.searchQuery
        val adapter = GamesAdapter(this)
        binding.toolbar.setNavigationOnClickListener {
            NavUtils.navigateUpFromSameTask(this.requireActivity())

        }
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(searchQuery: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(newSearchQuery: String): Boolean {
                searchQuery=newSearchQuery
                viewModel.searchQuery(newSearchQuery)
                viewModel.refreshData()

                return true
            }
        })
        val gamesRecyclerView = binding.searchResults

        gamesRecyclerView.adapter = adapter



        gamesRecyclerView.setItemViewCacheSize(50)
        binding.listTitle.text = getString(R.string.search_list_title, 0)
        searchQuery.also { query ->
            binding.searchBar.setQuery(query, false)
        }
        viewModel.search().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                NetworkResult.Status.SUCCESS -> {

                    if (response.data?.isEmpty() == true) {
                        binding.searchLoader.visibility = View.GONE
                        binding.emptySearchText.text=getString(R.string.empty_search_result,searchQuery)
                        binding.groupEmptyResult.visibility = View.VISIBLE
                    } else {
                        binding.listTitle.text =
                            getString(R.string.search_list_title, response.data?.size ?: 0)

                        adapter.submitList(response.data?.map { searchItem ->

                            val price = when (searchItem.price.split(" ","-").joinToString("").onlyLetters()) {
                                true -> getString(R.string.freeText)
                                false -> searchItem.price
                            }
                            GameItem(
                                searchItem.id,
                                searchItem.name,
                                emptyList(),
                                price,
                                searchItem.img,
                                searchItem.img)

                        })
                        binding.groupEmptyResult.visibility = View.GONE
                        binding.searchLoader.visibility = View.GONE
                        binding.groupSearchResult.visibility = View.VISIBLE
                    }

                }
                NetworkResult.Status.ERROR -> Toast.makeText(
                    this.requireContext(),
                    response.message,
                    Toast.LENGTH_LONG
                ).show()
                NetworkResult.Status.LOADING -> {
                    binding.groupEmptyResult.visibility = View.GONE
                    binding.groupSearchResult.visibility = View.GONE
                    binding.searchLoader.visibility = View.VISIBLE
                }
            }

        }
    }
    override fun onItemClick(item: GameItem) {
        findNavController().navigate(GameDetailsDirections.showGameDetails(item.id))
    }


}