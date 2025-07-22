package com.example.cats


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var breeds by mutableStateOf(listOf<Breed>())
    var filteredBreeds by mutableStateOf(listOf<Breed>())
    var error by mutableStateOf<String?>(null)
    var favorites by mutableStateOf(setOf<String>())
    var searchQuery by mutableStateOf("")

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getBreeds()
                breeds = result
                filteredBreeds = result
                error = null
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun toggleFavorite(breedId: String) {
        favorites = if (favorites.contains(breedId)) {
            favorites - breedId
        } else {
            favorites + breedId
        }
    }

    init {
        fetchBreeds()
    }

    fun updateSearch(query: String) {
        searchQuery = query
        filteredBreeds = if (query.isBlank()) {
            breeds
        } else {
            breeds.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}