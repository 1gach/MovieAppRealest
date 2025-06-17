package com.example.moviesapp2.UI.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesapp2.UI.Fragment.NowPlayingFragment
import com.example.moviesapp2.UI.Fragment.PopularFragment
import com.example.moviesapp2.UI.Fragment.TopRatedFragment
import com.example.moviesapp2.UI.Fragment.UpcomingFragment

class HomePagerAdapter( fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowPlayingFragment()
            1 -> UpcomingFragment()
            2 -> TopRatedFragment()
            3 -> PopularFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}