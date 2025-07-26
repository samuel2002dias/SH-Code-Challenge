package com.example.cats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * Composable screen displaying the list of favorite cat breeds.
 * Allows navigation back to the main list.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBreedsScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val favoriteBreeds = viewModel.breeds.filter { viewModel.favorites.contains(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Breeds") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoriteBreeds) { breed ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            AsyncImage(
                                model = breed.image?.url,
                                contentDescription = breed.name,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(
                                text = breed.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        val lowerLifespan = breed.life_span.split(" ").firstOrNull()?.toIntOrNull()
                        Text(
                            text = "Average lifespan: ${lowerLifespan?.toString() ?: "-"} years",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}