package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.models.Movie
import com.example.myapplication.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> = _movie

    fun loadMovie(movieId: Long) {
        viewModelScope.launch {
            _movie.value = repository.getMovieById(movieId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _movie.value?.let { currentMovie ->
                repository.toggleFavorite(currentMovie.id)
                _movie.value = repository.getMovieById(currentMovie.id)
            }
        }
    }

    fun toggleWatched(isWatched: Boolean) {
        viewModelScope.launch {
            _movie.value?.let { currentMovie ->
                if (currentMovie.isWatched != isWatched) {
                    repository.toggleWatched(currentMovie.id)
                    _movie.value = repository.getMovieById(currentMovie.id)
                }
            }
        }
    }
}