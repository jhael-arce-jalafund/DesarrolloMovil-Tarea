package com.example.practicabooksoffline.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Long,
    val title: String,
    val year: Int?,
    val imageUrl: String,
    val genre: String?,
    val rating: Double,
    val plot: String?,
    var isFavorite: Boolean,
    var isWatched: Boolean
)