package com.example.cats

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a favorite breed.
 */
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val breedId: String
)