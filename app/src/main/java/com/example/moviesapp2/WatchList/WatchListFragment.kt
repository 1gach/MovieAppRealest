package com.example.moviesapp2.WatchList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp2.MovieDetail.MovieDetailsViewModel
import com.example.moviesapp2.R
import com.example.moviesapp2.databinding.FragmentDetailBinding
import com.example.moviesapp2.databinding.FragmentWatchListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class WatchListFragment : Fragment() {
    private var _binding: FragmentWatchListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WatchlistViewModel by viewModels()
    private lateinit var adapter: WatchListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWatchListBinding.inflate(inflater, container, false)

        binding.watchListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
         adapter = WatchListAdapter()
        binding.watchListRecyclerView.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.watchListRecyclerView.setHasFixedSize(false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.watchlistMovies.collect { movieList ->
                adapter.submitList(movieList)
            }
        }

        binding.back.setOnClickListener {
            findNavController().navigate(
                R.id.homeFragment,
                Bundle().apply {  }
            )
            val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
            navBar.selectedItemId = R.id.homeFragment


        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        @JvmStatic
        fun newInstance() = WatchListFragment()

            }
    }
