package com.example.moviesapp2.Popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp2.NowPlaying.Movie
import com.example.moviesapp2.NowPlaying.MoviePagingAdapter
import com.example.moviesapp2.NowPlaying.NowPlayingFragment.MovieClickHandler
import com.example.moviesapp2.R
import com.example.moviesapp2.Upcoming.UpcomingViewModel
import com.example.moviesapp2.databinding.FragmentPopularBinding
import com.example.moviesapp2.databinding.FragmentUpcomingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularFragment : Fragment() {
    private  var _binding : FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PopularViewModel
    private lateinit var adapter: MoviePagingAdapter
    private var movieClickListener: ((Movie) -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagingAdapt()
        clickHandle()

    }

    private fun pagingAdapt(){
        adapter = MoviePagingAdapter { movie ->
            movieClickListener?.invoke(movie)
        }

        binding.recyclerMovie.layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        binding.recyclerMovie.adapter = adapter

        viewModel = ViewModelProvider(this)[PopularViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }



    private fun clickHandle(){
        if (parentFragment is MovieClickHandler) {
            movieClickListener = (parentFragment as MovieClickHandler)::onMovieClick
        } else {
            throw IllegalStateException("Parent fragment must implement MovieClickHandler")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {


        @JvmStatic
        fun newInstance() =
            PopularFragment()
            }
    }
