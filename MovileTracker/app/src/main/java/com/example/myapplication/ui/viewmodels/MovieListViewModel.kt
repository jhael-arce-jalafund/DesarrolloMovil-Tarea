package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.MovieService
import com.example.myapplication.db.models.Movie
import com.example.myapplication.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val movieService: MovieService
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val movieDtos = movieService.searchMovies("")

                movieDtos.forEach { dto ->
                    val existingMovie = repository.getMovieById(dto.id.toLong())
                    repository.insertMovie(dto.toMovieEntity(existingMovie))
                }

                _movies.value = repository.getAllMovies()
            } catch (e: Exception) {
                _error.value = "Error al cargar películas: ${e.message}"
                _movies.value = repository.getAllMovies()
            } finally {
                _isLoading.value = false
            }
        }
    }

}