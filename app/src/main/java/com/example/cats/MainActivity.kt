package com.example.cats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cats.ui.theme.CatsTheme

/**
 * Main activity hosting the navigation and UI for the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer { MainViewModel(application) }
                    }
                )
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "breedList",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("breedList") {
                            BreedListScreen(
                                viewModel = viewModel,
                                onBreedClick = { breedId ->
                                    navController.navigate("breedDetail/$breedId")
                                }
                            )
                        }
                        composable(
                            "breedDetail/{breedId}",
                            arguments = listOf(navArgument("breedId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val breedId = backStackEntry.arguments?.getString("breedId") ?: ""
                            BreedDetailScreen(
                                breedId = breedId,
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}