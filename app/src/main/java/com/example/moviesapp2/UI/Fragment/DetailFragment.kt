package com.example.moviesapp2.MovieDetail

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviesapp2.DetailsVPItems.DetailsPagerAdapter
import com.example.moviesapp2.R
import com.example.moviesapp2.WatchList.WatchlistViewModel
import com.example.moviesapp2.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var isBookmarked = false
    private var movieId: Int? = null

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val viewModel2: WatchlistViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        initListeners()
        setDetails()
        viewPager()
        checkBookmark()
        makeBlur()

        viewLifecycleOwner.lifecycleScope.launch {
            if (movieId != null) {
                isBookmarked = viewModel2.isInDb(movieId!!)
                updateBookmarkState()
            }
        }

        return binding.root
    }

    private fun makeBlur(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blur = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
            binding.starBack.setRenderEffect(blur)
        }
    }

    private fun initListeners() = with(binding) {
        back.setOnClickListener {
            findNavController().navigate(
                R.id.homeFragment,
                Bundle().apply {  }
            )
        }

        val toggleBookmark = View.OnClickListener {
            isBookmarked = !isBookmarked
            updateBookmarkState()
            toggleMovieInDb()

        }
        binding.removedFromWatchListIcon.setOnClickListener(toggleBookmark)
        binding.addedToWatchListIcon.setOnClickListener(toggleBookmark)

    }


    private fun viewPager() {
        val tabLayout = binding.tabLayout2
        val viewPager = binding.movieDescriptionViewPager

        val adapter = DetailsPagerAdapter(this, movieId!!)
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
        viewModel.fetchMovieDetails(movieId!!)

        viewModel.movieDetails.observe(viewLifecycleOwner) { details ->
            if (details != null) {
                binding.movieName.text = details.title
                binding.minutes.text = "${details.runtime} minutes"
                binding.releaseDate.text = details.releaseDate
                binding.star.text = details.voteAverage.toString()

                val posterUrl = "https://image.tmdb.org/t/p/w500" + details.poster_path
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

    private fun checkBookmark() {


    }

    private fun updateBookmarkState() {
        binding.removedFromWatchListIcon.visibility = if (isBookmarked) View.GONE else View.VISIBLE
        binding.addedToWatchListIcon.visibility = if (isBookmarked) View.VISIBLE else View.GONE
    }

    private fun toggleMovieInDb() {
        val details = viewModel.movieDetails.value
        viewModel2.toggleMovieInDb(details)
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

