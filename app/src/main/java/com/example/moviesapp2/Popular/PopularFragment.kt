package com.example.moviesapp2.Popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp2.R

class PopularFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    companion object {


        @JvmStatic
        fun newInstance() =
            PopularFragment()
            }
    }
