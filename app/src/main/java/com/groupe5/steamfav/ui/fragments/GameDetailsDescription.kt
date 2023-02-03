package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.groupe5.steamfav.databinding.FragmentGameDetailsDescriptionBinding
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel


class GameDetailsDescription() : Fragment() {
    private var _binding: FragmentGameDetailsDescriptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val viewModel: GameDetailsViewModel by viewModels({requireParentFragment()})
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameDetailsDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameDetailsDescription.setTextFuture(
            PrecomputedTextCompat.getTextFuture(
                HtmlCompat.fromHtml(viewModel.description() ?: "",HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH),
                TextViewCompat.getTextMetricsParams( binding.gameDetailsDescription),
                null
            )
        )
    }
    companion object {
        fun newInstance() =
            GameDetailsDescription()
    }
}