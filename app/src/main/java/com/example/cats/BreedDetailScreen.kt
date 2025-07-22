package com.example.cats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailScreen(
    breedId: String,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val breed = viewModel.breeds.find { it.id == breedId }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(breed?.name ?: "Breed Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        breed?.let {
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)) {
                it.image?.url?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text("Origin: ${it.origin}")
                Text("Temperament: ${it.temperament}")
                Text("Life Span: ${it.life_span}")
                Spacer(Modifier.height(8.dp))
                Text(it.description)
            }
        } ?: Text("Breed not found", modifier = Modifier.padding(16.dp))
    }
}