package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vk.kmprecipeapp.model.Recipe
import com.vk.kmprecipeapp.viewModel.ItemsViewModel

@Composable
fun CategoryScreen(
    viewModel: ItemsViewModel,
    navController: NavController
) {
    val state by viewModel.stateRecipes.collectAsStateWithLifecycle()
    val filteredItems = remember(state.recipesData.recipes) {
        state.recipesData.recipes.distinctBy { it.cuisine }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {

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
                        ItemCategory(
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


@Composable
fun ItemCategory(item: Recipe, navController: NavController, viewModel: ItemsViewModel) {

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
                .clickable(
                    onClick = {
                        viewModel.selectedCuisine(item.cuisine)
                        navController.navigate("categoryDetails")
                    }
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {


            Text(
                text = item.cuisine,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)

            )
        }

    }

}