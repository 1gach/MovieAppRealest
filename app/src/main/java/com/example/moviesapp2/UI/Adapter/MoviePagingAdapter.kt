package com.example.moviesapp2.UI.Adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviesapp2.Data.remote.models.Movie
import com.example.moviesapp2.databinding.ItemMoviesBinding




class MoviePagingAdapter  (private val onItemClick: (Movie) -> Unit
        ):PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            if (movie == null) {
                binding.movieImage.setImageDrawable(null)
            } else {
                val radiusInPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16f,
                    binding.movieImage.context.resources.displayMetrics
                ).toInt()
                val posterUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath
                Glide.with(binding.movieImage.context)
                    .load(posterUrl)
                    .transform(RoundedCorners(radiusInPx))
                    .into(binding.movieImage)

                binding.movieImage.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
