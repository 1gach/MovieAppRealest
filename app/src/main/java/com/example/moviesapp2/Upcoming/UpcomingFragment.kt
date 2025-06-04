package com.example.moviesapp2.Upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp2.R


class UpcomingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = UpcomingFragment()
            }
    }
