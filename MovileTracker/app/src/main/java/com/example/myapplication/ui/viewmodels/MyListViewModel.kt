package com.example.practicabooksoffline.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabooksoffline.db.models.Movie
import com.example.practicabooksoffline.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private var currentTab: Int = 0

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        currentTab = 0
        viewModelScope.launch {
            _movies.value = repository.getFavorites()
        }
    }

    fun loadWatched() {
        currentTab = 1
        viewModelScope.launch {
            _movies.value = repository.getWatched()
        }
    }

    fun toggleFavorite(movieId: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(movieId)
            refreshCurrentList()
        }
    }

    fun toggleWatched(movieId: Long, isChecked: Boolean) {
        viewModelScope.launch {
            repository.toggleWatched(movieId)
            refreshCurrentList()
        }
    }

    private fun refreshCurrentList() {
        when (currentTab) {
            0 -> loadFavorites()
            1 -> loadWatched()
        }
    }
}