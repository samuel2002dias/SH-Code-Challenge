package com.example.cats

import androidx.room.*

/**
 * DAO for accessing favorite breeds in the database.
 */
@Dao
interface FavoriteDao {
    /**
     * Gets all favorite breed IDs.
     */
    @Query("SELECT breedId FROM favorites")
    suspend fun getAllFavorites(): List<String>

    /**
     * Adds a breed to favorites.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    /**
     * Removes a breed from favorites.
     */
    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)
}