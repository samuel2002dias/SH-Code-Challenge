package com.example.cats

import androidx.room.*

/**
 * DAO for accessing breed data in the database.
 */
@Dao
interface BreedDao {
    /**
     * Gets all breeds from the database.
     */
    @Query("SELECT * FROM breeds")
    suspend fun getAllBreeds(): List<BreedEntity>

    /**
     * Inserts a list of breeds, replacing on conflict.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<BreedEntity>)

    /**
     * Deletes all breeds from the database.
     */
    @Query("DELETE FROM breeds")
    suspend fun clearBreeds()
}