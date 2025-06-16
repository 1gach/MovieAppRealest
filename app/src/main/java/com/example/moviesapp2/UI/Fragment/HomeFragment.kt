package com.example.moviesapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp2.UI.Adapter.ImageAdapter
import com.example.moviesapp2.UI.Adapter.HomePagerAdapter
import com.example.moviesapp2.Data.remote.models.Movie
import com.example.moviesapp2.UI.Fragment.NowPlayingFragment
import com.example.moviesapp2.UI.ViewModel.PopularViewModel
import com.example.moviesapp2.UI.Adapter.SearchPagingAdapter
import com.example.moviesapp2.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() ,  NowPlayingFragment.MovieClickHandler {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PopularViewModel
    private lateinit var searchAdapter: SearchPagingAdapter
//    private lateinit var mainViewModel: MainViewModel
//    private var showSearch = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewPager()
        posterRecycler()


        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                moveToSearchFragment()
            }
        }


        return binding.root


    }

    private fun moveToSearchFragment() {
         findNavController().navigate(
            R.id.searchFragment,
            Bundle().apply {  }
        )

}




    private fun viewPager(){
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        val adapter = HomePagerAdapter(this)
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
        viewModel = ViewModelProvider(this)[PopularViewModel::class.java]


        val adapter = ImageAdapter()
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = adapter

        val drawables = listOf<Int>(R.drawable.number_1,R.drawable.number_2 ,
            R.drawable.number_3,R.drawable.number_4,R.drawable.number_5)


        viewModel.popularPosters.observe(viewLifecycleOwner) { posters ->
            adapter.submitPosterList(posters)
        }
        adapter.submitNumberList(drawables)

        viewModel.load5PopularPosters()

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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {


        @JvmStatic
        fun newInstance() = HomeFragment()


    }

}


