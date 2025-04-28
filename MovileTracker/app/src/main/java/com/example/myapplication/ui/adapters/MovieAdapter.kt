package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.db.models.Movie

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var items: List<Movie> = emptyList()

    inner class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]
        with(holder.binding) {
            Glide.with(root)
                .load(movie.imageUrl)
                .into(ivMoviePoster)

            tvMovieTitle.text = movie.title

            root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Movie>) {
        items = newItems
        notifyDataSetChanged()
    }
}