package com.example.moviesapp2.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp2.UI.ViewModel.MovieDetailsViewModel
import com.example.moviesapp2.databinding.FragmentAboutMovieBinding


class AboutMovieFragment : Fragment() {
    private lateinit var viewModel: MovieDetailsViewModel
    private  var _binding: FragmentAboutMovieBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutMovieBinding.inflate(inflater, container, false)
        setAboutMovie()
        return binding.root
    }


    private fun setAboutMovie(){
        val movieId = arguments?.getInt("movieId") ?: return
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        viewModel.fetchMovieDetails(movieId)

        viewModel.movieDetails.observe(viewLifecycleOwner){item->
            if (item != null) {
                binding.aboutMovieText.text = item.overview
            }

        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() = AboutMovieFragment()

                }
            }



