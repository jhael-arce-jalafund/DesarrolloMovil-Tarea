package com.example.practicabooksoffline.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.practicabooksoffline.db.models.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    suspend fun getFavorites(): List<Movie>

    @Query("SELECT * FROM movies WHERE isWatched = 1")
    suspend fun getWatched(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getById(id: Long): Movie?

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<Movie?>
}