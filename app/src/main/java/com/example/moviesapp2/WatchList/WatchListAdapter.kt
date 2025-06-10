package com.example.moviesapp2.WatchList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp2.databinding.ItemWatchListBinding

class WatchListAdapter
//    (private val onDeleteClick: (List<WatchlistMovie>) -> Unit
//)
:  RecyclerView.Adapter<WatchListAdapter.WatchlistViewHolder>() {

    private val movies = mutableListOf<WatchlistMovie>()

    fun submitList(newList: List<WatchlistMovie>) {
        movies.clear()
        movies.addAll(newList)
        notifyDataSetChanged()
    }
//    fun removeList(oldList: List<WatchlistMovie>){
//        onDeleteClick(oldList)
//    }

    inner class WatchlistViewHolder(val binding: ItemWatchListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int ) {
            val data: WatchlistMovie = movies[position]
            binding.title.text = data.title
            binding.year.text = "Release: ${data.releaseDate}"
            binding.time.text = "Runtime: ${data.runtime} min"
            binding.star.text = data.star.toString()

            val posterUrl = "https://image.tmdb.org/t/p/w500${data.posterPath}"
            Glide.with(binding.watchPoster.context)
                .load(posterUrl)
                .into(binding.watchPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val binding = ItemWatchListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WatchlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = movies.size
}