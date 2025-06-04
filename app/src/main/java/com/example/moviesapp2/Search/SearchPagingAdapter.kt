package com.example.moviesapp2.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp2.databinding.ItemSearchBinding

class SearchPagingAdapter: PagingDataAdapter<Searches,SearchPagingAdapter.SearchViewHolder>(DiffCallback) {


    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Searches) {
            binding.titleText.text = movie.title
            binding.dateText.text = "Release Date: ${movie.release_date}"
            Glide.with(binding.searchPoster).load("https://image.tmdb.org/t/p/w500${movie.poster_path}").into(binding.searchPoster)
        }
    }

    override fun onBindViewHolder(holder: SearchPagingAdapter.SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPagingAdapter.SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    object DiffCallback : DiffUtil.ItemCallback<Searches>() {
        override fun areItemsTheSame(oldItem: Searches, newItem: Searches): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Searches, newItem: Searches): Boolean = oldItem == newItem
    }
}