package com.example.moviesapp2

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviesapp2.NowPlaying.Movie
import com.example.moviesapp2.databinding.ItemPosterBinding

class ImageAdapter(private val posters: List<Movie>) :
    RecyclerView.Adapter<ImageAdapter.PosterViewHolder>() {

    inner class PosterViewHolder(val binding: ItemPosterBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int) {
            val poster: Movie = posters[position]
            val posterUrl = "https://image.tmdb.org/t/p/w500" + poster.posterPath
//            Glide.with(binding.poster.context)
//                .load(posterUrl)
//                .transform(RoundedCorners(radiusInPx))
//                .into(binding.poster)
            Glide.with(binding.poster.context)
                .load("https://image.tmdb.org/t/p/w500/8YFL5QQVPy3AgrEQxNYVSgiPEbe.jpg")
                .into(binding.poster)
            

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
