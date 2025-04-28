package com.example.myapplication.ui.viewmodels

import android.widget.SearchView
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
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val movieService: MovieService
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadAllMovies()
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                if (query.isEmpty()) {
                    loadAllMovies()
                } else {
                    val movieDtos = movieService.searchMovies(query)

                    movieDtos.forEach { dto ->
                        val existingMovie = repository.getMovieById(dto.id.toLong())
                        repository.insertMovie(dto.toMovieEntity(existingMovie))
                    }

                    val results = repository.getAllMovies()
                        .filter {
                            it.title.contains(query, ignoreCase = true) ||
                                    it.genre?.contains(query, ignoreCase = true) == true
                        }
                        .sortedBy { it.title }

                    _searchResults.postValue(results)
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
                val localResults = repository.getAllMovies()
                    .filter {
                        query.isEmpty() ||
                                it.title.contains(query, ignoreCase = true) ||
                                it.genre?.contains(query, ignoreCase = true) == true
                    }
                _searchResults.postValue(localResults)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun loadAllMovies() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val allMovies = repository.getAllMovies()
                if (allMovies.isEmpty()) {
                    val movieDtos = movieService.searchMovies("")
                    movieDtos.forEach { dto ->
                        repository.insertMovie(dto.toMovieEntity(null))
                    }
                    _searchResults.postValue(repository.getAllMovies())
                } else {
                    _searchResults.postValue(allMovies)
                }
            } catch (e: Exception) {
                _error.postValue("Error loading movies: ${e.message}")
                _searchResults.postValue(repository.getAllMovies())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}