package com.example.cats
import com.squareup.moshi.JsonClass

/**
 * Data class representing a cat breed from the API.
 */
@JsonClass(generateAdapter = true)
data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val life_span: String,
    val image: BreedImage?
)

/**
 * Data class for breed image information.
 */
@JsonClass(generateAdapter = true)
data class BreedImage(
    val url: String?
)