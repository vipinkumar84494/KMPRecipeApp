package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vk.kmprecipeapp.model.LoginRequest
import com.vk.kmprecipeapp.utils.Cons
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun LoginScreen(navController: NavController,viewModel: ItemsViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val couroutineScope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = koinInject()

    var isState by remember { mutableStateOf(false) }

    val state by viewModel.stateLogin.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()




    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState,) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                if (isState){
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(modifier = Modifier
                                .align(Alignment.Center))
                        }
                        state.error != null -> {
                            isState = false
                            couroutineScope.launch {
                                snackbarHostState.showSnackbar(state.error!!)
                            }
                        }
                        state.loginResponse != null  && state.error == null -> {
                            isState = false
                            settingsViewModel.repo.saveAccessToken(accessToken = state.loginResponse!!.accessToken!!)
                            settingsViewModel.updateUserData(state.loginResponse!!)
                            settingsViewModel.updateProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGyogBYaoFIZhcm9Ei2LB4Vb3gczmCpLod4Q&s")
//                            settings.repo.saveUserData(Cons.USER_DATA,state.loginResponse!!)
//                            settings.repo.saveValue(Cons.PROFILE_PIC,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGyogBYaoFIZhcm9Ei2LB4Vb3gczmCpLod4Q&s")
                            navController.navigate("home"){
                                popUpTo("login"){
                                    inclusive = true
                                }
                            }
                        }

                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {


                    Text("Login",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        fontWeight = FontWeight.Bold



                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = email.trim(),
                        onValueChange = { email = it.trim() },
                        label = { Text("Username") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,

                            )
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(12.dp)
                            .padding(16.dp),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,

                            )

                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(16.dp),
                        onClick = {

                            if (email.isNullOrEmpty()){
                                couroutineScope.launch {
                                    snackbarHostState.showSnackbar("Please enter your username")

                                }

                            }
//                            if (!isValidEmail(email)){
//                                couroutineScope.launch {
//                                    snackbarHostState.showSnackbar("Please enter a valid email")
//
//                                }
//
//                            }
                            else if (password.isEmpty()){
                                couroutineScope.launch {
                                    snackbarHostState.showSnackbar("Please enter your password")

                                }
                            }
                            else if (password.length<6){
                                couroutineScope.launch {
                                    snackbarHostState.showSnackbar("Password should be greater than 6 characters")

                                }
                            }
                            else{
                                isState = true
                              viewModel.loginUser(LoginRequest(email.trim(),password.trim(),30))


                            }


                        }
                    ){
                        Text("Login")
                    }

                }

            }
        }
    )



}
