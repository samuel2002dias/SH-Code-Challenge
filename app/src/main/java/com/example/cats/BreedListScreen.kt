package com.example.cats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment

@Composable
fun BreedListScreen(
    viewModel: MainViewModel,
    onBreedClick: (String) -> Unit,
    onShowFavorites: () -> Unit
) {
    val error = viewModel.error
    val searchQuery = viewModel.searchQuery
    val breeds = viewModel.filteredBreeds

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearch(it) },
            label = { Text("Search breeds") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (error != null) {
            Text(
                text = "Error: $error",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(breeds) { breed ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onBreedClick(breed.id) },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = breed.image?.url,
                                contentDescription = breed.name,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(end = 16.dp)
                            )
                            Text(
                                text = breed.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.toggleFavorite(breed.id) }
                            ) {
                                val isFavorite = viewModel.favorites.contains(breed.id)
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = if (isFavorite) "Unmark Favorite" else "Mark Favorite",
                                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = onShowFavorites,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Show Favorites")
        }
    }
}