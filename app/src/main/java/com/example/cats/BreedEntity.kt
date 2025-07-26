package com.example.cats

import androidx.room.*

/**
 * Room entity representing a cat breed.
 */
@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val life_span: String,
    @Embedded val image: BreedImageEntity?
)

/**
 * Embedded entity for breed image data.
 */
data class BreedImageEntity(
    val url: String?
)

/**
 * Converts a BreedEntity to a Breed.
 */
fun BreedEntity.toBreed() = Breed(id, name, origin, temperament, description, life_span, image?.toBreedImage())

/**
 * Converts a BreedImageEntity to a BreedImage.
 */
fun BreedImageEntity.toBreedImage() = BreedImage(url)

/**
 * Converts a Breed to a BreedEntity.
 */
fun Breed.toEntity() = BreedEntity(id, name, origin, temperament, description, life_span, image?.toEntity())

/**
 * Converts a BreedImage to a BreedImageEntity.
 */
fun BreedImage.toEntity() = BreedImageEntity(url)