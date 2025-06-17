package com.example.moviesapp2.UI.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesapp2.UI.Fragment.AboutMovieFragment
import com.example.moviesapp2.UI.Fragment.CastFragment
import com.example.moviesapp2.UI.Fragment.ReviewsFragment

class DetailsPagerAdapter( fragment: Fragment,
                           private val movieId: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> AboutMovieFragment()
            1 -> ReviewsFragment()
            2 -> CastFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
        fragment.arguments = Bundle().apply {
            putInt("movieId", movieId)
        }
        return fragment
    }

}