package com.example.moviesapp2.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp2.MainActivity
import com.example.moviesapp2.R
import com.example.moviesapp2.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchPagingAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        listener()
        adapter()
        return binding.root
    }


    private fun adapter(){
        val showSearch = arguments?.getBoolean("showSearch") ?: false

        searchAdapter = SearchPagingAdapter()
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
            visibility = if (showSearch) View.VISIBLE else View.GONE
        }
    }



    private fun listener(){
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        viewModel.setQuery(it)
                    } else {
                        // Submit empty list if query is blank
                        lifecycleScope.launch {
                            searchAdapter.submitData(PagingData.empty())
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty()) {
                    // User clicked X or cleared the input → clear RecyclerView
                    lifecycleScope.launch {
                        searchAdapter.submitData(PagingData.empty())
                    }
                }
                return true
            }
        })

        lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                searchAdapter.submitData(pagingData)
            }
        }

        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showSearchView()
            }
        }
        binding.closeSearchBtn.setOnClickListener{
            hideSearchView()
        }
    }


    private fun showSearchView() {
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.closeSearchBtn.visibility = View.VISIBLE
    }

    private fun hideSearchView() {
        findNavController().navigate(
            R.id.homeFragment,
            Bundle().apply { putBoolean("showSearch", false) }
        )
        binding.searchRecyclerView.visibility = View.GONE
        binding.closeSearchBtn.visibility = View.GONE
        binding.searchBar.setQuery("", false)
        binding.searchBar.clearFocus()
        (activity as? MainActivity)?.binding?.bottomNav?.selectedItemId = R.id.searchFragment
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment()


    }
}
