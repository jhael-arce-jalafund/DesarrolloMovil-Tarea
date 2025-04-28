package com.example.practicabooksoffline.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicabooksoffline.databinding.ItemMyListBinding
import com.example.practicabooksoffline.db.models.Movie

class MyListAdapter(
    private val onFavoriteClick: (Long) -> Unit,
    private val onWatchedClick: (Long, Boolean) -> Unit,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    private var items: List<Movie> = emptyList()

    inner class ViewHolder(val binding: ItemMyListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(root.context)
                    .load(movie.imageUrl)
                    .into(imagePoster)

                textTitle.text = movie.title

                val favoriteIcon = if (movie.isFavorite) {
                    com.example.practicabooksoffline.R.drawable.ic_favorite_filled
                } else {
                    com.example.practicabooksoffline.R.drawable.ic_favorite_border
                }
                imageFavorite.setImageResource(favoriteIcon)

                checkWatchedAll.isChecked = movie.isWatched

                imageFavorite.setOnClickListener {
                    onFavoriteClick(movie.id)
                }

                checkWatchedAll.setOnCheckedChangeListener { _, isChecked ->
                    onWatchedClick(movie.id, isChecked)
                }

                root.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Movie>) {
        items = newItems
        notifyDataSetChanged()
    }
}