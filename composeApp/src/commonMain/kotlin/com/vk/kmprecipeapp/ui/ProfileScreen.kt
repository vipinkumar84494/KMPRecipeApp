package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vk.kmprecipeapp.utils.Cons
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmprecipeapp.composeapp.generated.resources.Res
import kmprecipeapp.composeapp.generated.resources.left_arrow
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import org.jetbrains.compose.resources.painterResource



@Composable
fun ProfileScreen(navController: NavController){

    val snackbarHostState = remember { SnackbarHostState() }
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    val settings = remember { SettingsViewModel() }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val imageCropper = rememberImageCropper()
    var selectedImage by remember {mutableStateOf<ImageBitmap?>(null)}
    var openImagePicker by remember { mutableStateOf(false) }

    val userData = settings.repo.getUserData(Cons.USER_DATA)

    val profilePic = settings.repo.getValue(Cons.PROFILE_PIC)


    LogOutDialog(
        show = showLogoutDialog,
        onConfirm = {
            navController.navigate("login") {
                settings.repo.clear()
                popUpTo("home") { inclusive = true }

            }
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState,) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            {

                Column(
                    modifier = Modifier
                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                ) {

                    Row( modifier = Modifier
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
//                        Image(
//                            painter = painterResource(Res.drawable.sign_out),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .size(24.dp)
//                                .clickable {
//                                    showLogoutDialog = true
//
//                                },
//                        )
                    }


                    if (selectedImage != null){
                        Image(
                            bitmap = selectedImage!!,
                            contentDescription = null,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(10.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    else {
                        KamelImage(
                            resource = asyncPainterResource(profilePic),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(10.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)

                        )
                    }


                    CMPImagePickNCropDialog(
                        imageCropper = imageCropper,
                        openImagePicker = openImagePicker,
                        imagePickerDialogHandler = {
                            openImagePicker = it
                        },
                        selectedImageCallback = {
                            selectedImage = it
                        },
                        selectedImageFileCallback = {

                        })

                    Text("Change Profile Pic",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .clickable(onClick = {
                                openImagePicker = true
                            }),
                        fontWeight = FontWeight.Bold



                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        readOnly = true,
                        value = userData?.username!!.trim(),
                        onValueChange = { username = it.trim() },
                        label = { Text("Username") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,

                            ),
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(12.dp)
                            .padding(16.dp),
                        readOnly = true,
                        value = userData.firstName!!.trim()+" "+userData.lastName!!.trim(),
                        onValueChange = { name = it },
                        label = { Text("Name") },
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
                        readOnly = true,
                        value = userData.email!!.trim(),
                        onValueChange = { name = it },
                        label = { Text("Email") },
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
                        readOnly = true,
                        value = userData.gender!!.trim(),
                        onValueChange = { gender = it },
                        label = { Text("Gender") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,

                            )
                    )
                }

            }
        }
    )
}

@Composable
fun LogOutDialog(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    if (show){
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text("Logout")
            },
            text = {
                Text("Are you sure you want to logout?")
            },
            confirmButton ={
                TextButton(onClick = onConfirm){
                    Text("Logout")
                }
            },

            dismissButton = {
                TextButton(onClick = onDismiss){
                    Text("Cancel")
                }
            },
        )
    }
}
