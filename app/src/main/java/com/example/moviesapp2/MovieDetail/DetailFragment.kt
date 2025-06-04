package com.example.moviesapp2.MovieDetail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp2.DetailsVPItems.DetailsPagerAdapter
import com.example.moviesapp2.NowPlaying.HomePagerAdapter
import com.example.moviesapp2.NowPlaying.Movie
import com.example.moviesapp2.R
import com.example.moviesapp2.databinding.FragmentDetailBinding
import com.example.moviesapp2.databinding.FragmentNowPlayingBinding
import com.google.android.material.tabs.TabLayoutMediator


class DetailFragment : Fragment() {
    private lateinit var viewModel: MovieDetailsViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var movieId: Int? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetails()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setDetails()
        viewPager()
        return binding.root
    }


    private fun viewPager() {
        val tabLayout = binding.tabLayout2
        val viewPager = binding.movieDescriptionViewPager

        val movieId = arguments?.getInt("movieId") ?: return
        val adapter = DetailsPagerAdapter(this, movieId)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "About Movie"
                1 -> "Reviews"
                2 -> "Cast"
                else -> null
            }
        }.attach()
    }

    private fun setDetails() {
        movieId = arguments?.getInt("movieId")
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        viewModel.fetchMovieDetails(movieId!!)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.movieDetails.observe(viewLifecycleOwner) { details ->
            if (details != null) {
                binding.movieName.text = details.title
                binding.minutes.text = "${details.runtime} minutes"
                binding.releaseDate.text = details.releaseDate

                val posterUrl = "https://image.tmdb.org/t/p/w500"+ details.poster_path
                val coverUrl = "https://image.tmdb.org/t/p/w780" + details.backdrop_path

                Glide.with(requireContext())
                    .load(posterUrl)
                    .into(binding.imageMain)
                Glide.with(requireContext())
                    .load(coverUrl)
                    .into(binding.imageCover)


            }

        }
    }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        companion object {

            @JvmStatic
            fun newInstance() = DetailFragment()
        }
    }

