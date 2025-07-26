package com.example.cats

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel for managing breed and favorite data, including offline persistence.
 */
class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getDatabase(app)
    private val breedDao = db.breedDao()
    private val favoriteDao = db.favoriteDao()

    /** List of all breeds. */
    var breeds by mutableStateOf(listOf<Breed>())
    /** List of breeds filtered by search. */
    var filteredBreeds by mutableStateOf(listOf<Breed>())
    /** Error message, if any. */
    var error by mutableStateOf<String?>(null)
    /** Set of favorite breed IDs. */
    var favorites by mutableStateOf(setOf<String>())
    /** Current search query. */
    var searchQuery by mutableStateOf("")

    /**
     * Loads breeds from the local database, then updates from the API if possible.
     */
    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                // Load from local DB first
                val localBreeds = breedDao.getAllBreeds().map { it.toBreed() }
                breeds = localBreeds
                filteredBreeds = localBreeds

                // Try to fetch from API
                val result = try {
                    RetrofitClient.api.getBreeds()
                } catch (e: Exception) {
                    null // API failed, stay offline
                }

                if (result != null) {
                    breeds = result
                    filteredBreeds = result
                    error = null
                    breedDao.clearBreeds()
                    breedDao.insertBreeds(result.map { it.toEntity() })
                }
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    /**
     * Toggles a breed as favorite or not.
     *
     * @param breedId The ID of the breed to toggle.
     */
    fun toggleFavorite(breedId: String) {
        viewModelScope.launch {
            if (favorites.contains(breedId)) {
                favoriteDao.removeFavorite(FavoriteEntity(breedId))
                favorites = favorites - breedId
            } else {
                favoriteDao.addFavorite(FavoriteEntity(breedId))
                favorites = favorites + breedId
            }
        }
    }

    init {
        fetchBreeds()
        viewModelScope.launch {
            favorites = favoriteDao.getAllFavorites().toSet()
        }
    }

    /**
     * Updates the search query and filters the breed list.
     *
     * @param query The search string.
     */
    fun updateSearch(query: String) {
        searchQuery = query
        filteredBreeds = if (query.isBlank()) {
            breeds
        } else {
            breeds.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}