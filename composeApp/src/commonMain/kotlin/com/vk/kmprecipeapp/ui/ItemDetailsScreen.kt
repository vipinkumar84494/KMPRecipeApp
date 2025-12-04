package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vk.kmprecipeapp.utils.Cons.Companion.SELECTED_CATEGORY
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmprecipeapp.composeapp.generated.resources.Res
import kmprecipeapp.composeapp.generated.resources.left_arrow
import org.jetbrains.compose.resources.painterResource

@Composable
fun ItemDetailsScreen(viewModel: ItemsViewModel, navController: NavController) {
    val item = viewModel.getSelectedCategory()
    val settings = remember { SettingsViewModel() }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .verticalScroll(
                scrollState
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(Res.drawable.left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable {
                        navController.navigateUp()
                    },

                )
        }

        Card(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            KamelImage(
                resource = asyncPainterResource(item!!.image),
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
        println("url ${item!!.image}")

        settings.repo.saveValue(SELECTED_CATEGORY, "${item.name}")

        Text(
            text = item.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp, bottom = 16.dp)

        )
        val type =
            "Prep Time: ${item.prepTimeMinutes}min | Cook Time: ${item.cookTimeMinutes}min | Servings: ${item.servings}" +
                    " | Calories: ${item.caloriesPerServing} | Difficulty: ${item.difficulty} | Cuisine: ${item.cuisine} " +
                    "| Meal Type: ${item.mealType} | Rating: ${item.rating} | Review Count: ${item.reviewCount}"
        Text(
            text = type,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp)

        )
        val ingredients = item.ingredients.mapIndexed { index, item ->
            "${index + 1}. $item"
        }.joinToString("\n")
        Text(
            text = ingredients ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Text(
            text = "instructions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp)

        )
        val instructions = item.instructions.mapIndexed { index, item ->
            "${index + 1}. $item"
        }.joinToString("\n")
        Text(
            text = instructions ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        )
    }
}

