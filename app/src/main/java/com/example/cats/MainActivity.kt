package com.example.cats

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cats.ui.theme.CatsTheme
import kotlinx.coroutines.launch
import kotlin.collections.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BreedScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

class MainViewModel : ViewModel() {
    var breeds by mutableStateOf<List<Breed>>(emptyList())
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                breeds = RetrofitClient.api.getBreeds()
                error = null
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching breeds", e)
                error = e.message
            }
        }
    }
}

@Composable
fun BreedScreen(modifier: Modifier = Modifier) {
    val viewModel = remember { MainViewModel() }
    val breeds = viewModel.breeds
    val error = viewModel.error

    var breedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.fetchBreeds()
    }

    Column(modifier = modifier.padding(16.dp)) {
        if (error != null) {
            Text("Error: $error")
        } else if (breeds.isEmpty()) {
            Text("Loading breeds...")
        } else {
            val maxIndex = breeds.size - 1
            val breed = breeds[breedIndex]

            Text("Breed ${breedIndex + 1} of ${breeds.size}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text("Name: ${breed.name}")
            Text("Origin: ${breed.origin}")
            Text("Temperament: ${breed.temperament}")
            Text("Description: ${breed.description}")
            Text("Life Span: ${breed.life_span}")
            Spacer(Modifier.height(16.dp))
            Row {
                IconButton(
                    onClick = { if (breedIndex > 0) breedIndex-- },
                    enabled = breedIndex > 0
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                }
                Spacer(Modifier.width(16.dp))
                IconButton(
                    onClick = { if (breedIndex < maxIndex) breedIndex++ },
                    enabled = breedIndex < maxIndex
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun BreedScreenPreview() {
    CatsTheme {
        BreedScreen()
    }
}
