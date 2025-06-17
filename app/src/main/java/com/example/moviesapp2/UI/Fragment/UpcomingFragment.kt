package com.example.moviesapp2.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp2.Data.remote.models.Movie
import com.example.moviesapp2.UI.Adapter.MoviePagingAdapter
import com.example.moviesapp2.UI.Fragment.NowPlayingFragment.MovieClickHandler
import com.example.moviesapp2.UI.ViewModel.UpcomingViewModel
import com.example.moviesapp2.databinding.FragmentUpcomingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class UpcomingFragment : Fragment() {
    private  var _binding : FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UpcomingViewModel
    private lateinit var adapter: MoviePagingAdapter
    private var movieClickListener: ((Movie) -> Unit)? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
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

        viewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.upcomingMovies.collectLatest { pagingData ->
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
        fun newInstance() = UpcomingFragment()
            }
    }
