package com.groupe5.steamfav.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.groupe5.steamfav.ui.fragments.GameDetailsDescription
import com.groupe5.steamfav.ui.fragments.ReviewsFragment

class GameDetailsViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GameDetailsDescription.newInstance()
            else -> ReviewsFragment.newInstance()
        }

    }
}