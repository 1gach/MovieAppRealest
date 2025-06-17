package com.example.moviesapp2.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp2.UI.ViewModel.MovieDetailsViewModel
import com.example.moviesapp2.databinding.FragmentReviewsBinding


class ReviewsFragment : Fragment() {
    private lateinit var viewModel: MovieDetailsViewModel
    private  var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        setReview()
        return binding.root
    }


    private fun setReview(){
        val movieId = arguments?.getInt("movieId") ?: return
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        viewModel.fetchMovieReviews(movieId)

        viewModel.reviews.observe(viewLifecycleOwner){items->
            if (!items.isNullOrEmpty()) {
                // Just show the first cast member's name for now
                binding.reviewText.text = items.joinToString(", ") { it.author }
            } else {
                binding.reviewText.text = "No cast available"
            }

        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        @JvmStatic
        fun newInstance() = ReviewsFragment()

    }
}