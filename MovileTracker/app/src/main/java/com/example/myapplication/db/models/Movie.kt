package com.example.myapplication.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Long,
    val title: String,
    val imageUrl: String,
    var isFavorite: Boolean,
    var isWatched: Boolean
)