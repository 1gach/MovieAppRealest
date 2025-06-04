package com.example.moviesapp2.NowPlaying

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesapp2.Popular.PopularFragment
import com.example.moviesapp2.TopRated.TopRatedFragment
import com.example.moviesapp2.Upcoming.UpcomingFragment

class HomePagerAdapter( fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->NowPlayingFragment()
            1 ->UpcomingFragment()
            2 ->TopRatedFragment()
            3 -> PopularFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}