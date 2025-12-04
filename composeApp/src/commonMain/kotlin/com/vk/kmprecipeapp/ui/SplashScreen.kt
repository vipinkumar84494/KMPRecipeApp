package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import kmprecipeapp.composeapp.generated.resources.Res
import kmprecipeapp.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(navController: NavController) {
    val settings = remember { SettingsViewModel() }
    LaunchedEffect(Unit){
        delay(1500)
        if (settings.repo.getAccessToken().isEmpty()){
            navController.navigate("login"){
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }
        else {
            navController.navigate("home"){
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }

    }
    Image(
        painter = painterResource(Res.drawable.compose_multiplatform),
        contentDescription = null,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        alignment = Alignment.Center
    )
}