package com.example.moviesapp2.TopRated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp2.R


class TopRatedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = TopRatedFragment()
            }
    }
