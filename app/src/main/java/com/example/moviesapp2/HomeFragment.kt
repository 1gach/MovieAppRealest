package com.example.moviesapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moviesapp2.NowPlaying.HomePagerAdapter
import com.example.moviesapp2.NowPlaying.Movie
import com.example.moviesapp2.NowPlaying.MoviePagingAdapter
import com.example.moviesapp2.NowPlaying.MovieViewModel
import com.example.moviesapp2.NowPlaying.NowPlayingFragment
import com.example.moviesapp2.Search.MainViewModel
import com.example.moviesapp2.Search.SearchPagingAdapter
import com.example.moviesapp2.Search.SearchViewModel
import com.example.moviesapp2.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest


class HomeFragment : Fragment() ,  NowPlayingFragment.MovieClickHandler {

    private  var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchPagingAdapter
    private lateinit var mainViewModel: MainViewModel
    private var showSearch = false





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewPager()
        posterRecycler()
        val showSearch = arguments?.getBoolean("showSearch") ?: false

        adapter = SearchPagingAdapter()
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
            visibility = if (showSearch) View.VISIBLE else View.GONE
        }

        searchAdapt()
        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showSearchView()
            }
        }
        binding.closeSearchBtn.setOnClickListener{
            hideSearchView()
        }

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        mainViewModel.showSearchResults.observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                binding.searchRecyclerView.visibility = View.VISIBLE
                binding.searchBar.isIconified = false
                mainViewModel.showSearchResults.value = false // Reset
            }
        }

        return binding.root


    }


    private fun viewPager(){
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        val adapter = HomePagerAdapter(this) // Your FragmentStateAdapter providing 4 fragments
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Now playing"
                1 -> "Upcoming"
                2 -> "Top rated"
                3 -> "Popular"
                else -> null
            }
        }.attach()
    }

    private fun posterRecycler(){
        val movieList = listOf(
            Movie(1, "/8YFL5QQVPy3AgrEQxNYVSgiPEbe.jpg"),
            Movie(2, "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg"),
            Movie(3, "/6EdKBYkB1ssgGjc249ud1L55o8d.jpg"),
            Movie(4, "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"),
            Movie(5, "/4JIz6QH5bZi2Ck8Wl8SxTV0f6pN.jpg")
        )


        val adapter = ImageAdapter(movieList.take(5))
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.recycler.adapter = adapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
    override fun onMovieClick(movie: Movie) {
        val bundle = Bundle().apply {
            putInt("movieId", movie.id)
        }
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }


    private fun searchAdapt(){
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        viewModel.setQuery(it)
                    } else {
                        // Submit empty list if query is blank
                        lifecycleScope.launch {
                            adapter.submitData(PagingData.empty())
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty()) {
                    // User clicked X or cleared the input → clear RecyclerView
                    lifecycleScope.launch {
                        adapter.submitData(PagingData.empty())
                    }
                }
                return true
            }
        })

        lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }


    }


    private fun showSearchView() {
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.closeSearchBtn.visibility = View.VISIBLE
    }

    private fun hideSearchView() {
        binding.searchRecyclerView.visibility = View.GONE
        binding.closeSearchBtn.visibility = View.GONE
        findNavController().navigate(
            R.id.homeFragment,
            Bundle().apply { putBoolean("showSearch", false) }
        )
        binding.searchBar.setQuery("", false)
        binding.searchBar.clearFocus()
        (activity as? MainActivity)?.binding?.bottomNav?.selectedItemId = R.id.homeFragment
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {


        @JvmStatic
        fun newInstance() = HomeFragment()


    }

}


