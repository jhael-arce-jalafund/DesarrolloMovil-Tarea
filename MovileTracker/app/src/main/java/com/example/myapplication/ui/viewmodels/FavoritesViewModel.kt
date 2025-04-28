package com.example.practicabooksoffline.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabooksoffline.db.models.Movie
import com.example.practicabooksoffline.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _favorites = MutableStateFlow<List<Movie>>(emptyList())
    val favorites = _favorites.asStateFlow()

    init { loadFavorites() }

    private fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }
    fun toggleFavorite(movieId: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(movieId)
            loadFavorites()
        }
    }
}