package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import kmprecipeapp.composeapp.generated.resources.Res
import kmprecipeapp.composeapp.generated.resources.left_arrow
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryDetailsScreen(
    viewModel: ItemsViewModel,
    navController: NavController
) {
    val state by viewModel.stateRecipes.collectAsStateWithLifecycle()

    val cuisine = viewModel.getSelectedCuisine()

    val filteredItems = remember(state.recipesData.recipes, cuisine) {
        state.recipesData.recipes
            .filter {
                it.cuisine.trim().equals(cuisine?.trim(), ignoreCase = true)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),


        ) {
            Image(
                painter = painterResource(Res.drawable.left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigateUp()
                    },

                )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cuisine ?: "",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                )
            }

        }

        when {

            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            filteredItems.isEmpty() -> {
                Text(
                    text = "No items found",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else -> {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredItems) { item ->
                        ItemRow(
                            item = item,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}


