package com.example.moviesapp2.DetailsVPItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp2.MovieDetail.MovieDetailsViewModel
import com.example.moviesapp2.R
import com.example.moviesapp2.databinding.FragmentAboutMovieBinding
import com.example.moviesapp2.databinding.FragmentCastBinding


class CastFragment : Fragment() {
    private lateinit var viewModel: MovieDetailsViewModel
    private  var _binding: FragmentCastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCastBinding.inflate(inflater, container, false)
        setCast()
        return binding.root
    }


    private fun setCast(){
        val movieId = arguments?.getInt("movieId") ?: return
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        viewModel.fetchMovieCredits(movieId)

        viewModel.cast.observe(viewLifecycleOwner){items->
            if (!items.isNullOrEmpty()) {

                binding.castText.text = items.joinToString(", ") { it.name }
            } else {
                binding.castText.text = "No cast available"
            }

        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CastFragment()
    }
}