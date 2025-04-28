package com.example.myapplication.api.dto

import com.example.myapplication.db.models.Movie
import java.io.Serializable

typealias MovieDtos = List<MovieDto>

data class MovieDto(
    val id: Int,
    val title: String,
    val imageUrl: String,

) : Serializable {
    fun toMovieEntity(existing: Movie?): Movie {
        return Movie(
            id = id.toLong(),
            title = title,
            imageUrl = imageUrl,
            isFavorite = false,
            isWatched = false
        )
    }
}