package com.example.practicabooksoffline.api.dto

import com.example.practicabooksoffline.db.models.Movie
import java.io.Serializable

typealias MovieDtos = List<MovieDto>

data class MovieDto(
    val id: Int,
    val title: String,
    val year: Int,
    val imageUrl: String,
    val genre: String,
    val rating: Double,
    val plot: String
) : Serializable {
    fun toMovieEntity(existing: Movie?): Movie {
        return Movie(
            id = id.toLong(),
            title = title,
            year = year,
            imageUrl = imageUrl,
            genre = genre,
            rating = rating,
            plot = plot,
            isFavorite = false,
            isWatched = false
        )
    }
}