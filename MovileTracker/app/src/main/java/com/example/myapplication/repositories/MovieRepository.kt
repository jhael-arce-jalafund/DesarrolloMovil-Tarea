package com.example.practicabooksoffline.repositories

import com.example.practicabooksoffline.api.MovieService
import com.example.practicabooksoffline.db.dao.MovieDao
import com.example.practicabooksoffline.db.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val dao: MovieDao,
    private val api: MovieService
) {

    suspend fun getAllMovies(): List<Movie> {
        return dao.getAllMovies()
    }

    suspend fun getMovieById(id: Long): Movie? {
        return dao.getById(id)
    }

    suspend fun insertMovie(movie: Movie) {
        dao.insertMovie(movie)
    }

    suspend fun toggleFavorite(movieId: Long) {
        val movie = dao.getById(movieId) ?: return
        movie.isFavorite = !movie.isFavorite
        dao.updateMovie(movie)
    }

    suspend fun toggleWatched(movieId: Long) {
        val movie = dao.getById(movieId) ?: return
        movie.isWatched = !movie.isWatched
        dao.updateMovie(movie)
    }

    suspend fun getFavorites() = dao.getFavorites()
    suspend fun getWatched() = dao.getWatched()

    suspend fun setWatchedStatus(movieId: Long, isWatched: Boolean) {
        val movie = dao.getById(movieId) ?: return
        movie.isWatched = isWatched
        dao.updateMovie(movie)
    }

    fun getMovieId(id: Long): Flow<Movie?> {
        return dao.getByIdFlow(id)
    }
}