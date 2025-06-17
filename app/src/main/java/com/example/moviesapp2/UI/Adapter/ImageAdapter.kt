package com.example.moviesapp2.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp2.Data.remote.models.Movie
import com.example.moviesapp2.databinding.ItemPosterBinding

class ImageAdapter :
    RecyclerView.Adapter<ImageAdapter.PosterViewHolder>() {

    private val posters = mutableListOf<Movie>()
    private val numbers = mutableListOf<Int>()

    fun submitPosterList(newList: List<Movie>) {
        posters.clear()
        posters.addAll(newList)
        notifyDataSetChanged()
    }

    fun submitNumberList(numList : List<Int>){
        numbers.addAll(numList)
    }


    inner class PosterViewHolder(val binding: ItemPosterBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int) {
            val poster: Movie = posters[position]
            val posterUrl = "https://image.tmdb.org/t/p/w500${poster.posterPath}"
             Glide.with(binding.poster.context)
                .load(posterUrl)
                .into(binding.poster)

            val num: Int =  numbers[position]
            binding.number.setImageResource(num)
        }
    }
    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.onBind(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val binding = ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterViewHolder(binding)
    }


    override fun getItemCount(): Int = posters.size
}
