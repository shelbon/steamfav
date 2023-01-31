package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.groupe5.steamfav.R
import com.groupe5.steamfav.databinding.FragmentReviewsBinding
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel

/**
 * A fragment representing a list of Items.
 */
class ReviewsFragment : Fragment() {


    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameDetailsViewModel by viewModels({ requireParentFragment() })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ReviewsAdapter()
        binding.reviews.adapter=adapter
        viewModel.reviews().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                NetworkResult.Status.SUCCESS -> {
                    if(response.data?.isNotEmpty() == true) {
                        with(response.data) {
                            adapter.submitList(this)
                            binding.loader.visibility = View.GONE
                            binding.reviewsStatusInformation.visibility = View.GONE
                            binding.reviews.visibility = View.VISIBLE
                        }
                    }else{
                        binding.loader.visibility = View.GONE
                        binding.reviewsStatusInformation.visibility = View.VISIBLE
                        binding.reviewsStatusInformation.text = getString(R.string.no_reviews)
                    }

                }
                NetworkResult.Status.ERROR -> {
                    binding.loader.visibility = View.GONE
                    binding.reviews.visibility = View.GONE
                    binding.reviewsStatusInformation.visibility = View.VISIBLE
                    binding.reviewsStatusInformation.text = response.message
                }
                NetworkResult.Status.LOADING -> {
                    binding.loader.visibility = View.VISIBLE
                    binding.reviewsStatusInformation.visibility = View.GONE
                    binding.reviews.visibility = View.GONE

                }
            }
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() =
            ReviewsFragment()

    }
}