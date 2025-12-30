package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vk.kmprecipeapp.utils.Cons
import com.vk.kmprecipeapp.utils.Cons.Companion.SELECTED_CATEGORY
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import com.vk.kmprecipeapp.viewModel.PermissionViewModel
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmprecipeapp.composeapp.generated.resources.Res
import kmprecipeapp.composeapp.generated.resources.menu
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(viewModelPer: PermissionViewModel = getKoin().get()) {


    val viewModel: ItemsViewModel = koinInject()
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val bottomNavRoutes = listOf("home", "search","category", "profile")
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
           DrawerContent(navController,drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                if (currentRoute in bottomNavRoutes) {
                    TopAppBar(getTopBarTitle(currentRoute), navController, drawerState)
                }
            },
            bottomBar = {
                if (currentRoute in bottomNavRoutes) {
                    AppBottomBar(navController)
                }

            }
        ){ innerPadding ->
            NavHost(navController = navController, startDestination = "splash",
                modifier = Modifier.padding(innerPadding)) {
                composable("splash"){
                    SplashScreen(navController)
                }
                composable("login"){
                    LoginScreen(navController,viewModel)
                }
                composable("home") {
                    ItemsScreen(viewModel, navController)

                }

                composable("item/{itemId}") { backStackEntry ->
                    ItemDetailsScreen(viewModel,navController)
                }
                composable("profile"){
                    ProfileScreen(navController,viewModel)
                }
                composable("search"){
                    SearchItemScreen(viewModel,navController)
                }
                composable("category"){
                    CategoryScreen(viewModel,navController)
                }
                composable("categoryDetails"){
                    CategoryDetailsScreen(viewModel,navController)
                }
            }
        }
    }



    scope.launch {
        viewModelPer.askNotificationPermission()
    }


}

@Composable
fun DrawerContent(navController: NavController,drawerState: DrawerState){

    val settingsViewModel: SettingsViewModel = koinInject()
    val userData by settingsViewModel.userData.collectAsState()
    val profilePic by settingsViewModel.profilePic.collectAsState()

    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }



    LogOutDialog(
        show = showLogoutDialog,
        onConfirm = {
            showLogoutDialog = false
            navController.navigate("login") {
                settingsViewModel.clearData()
                popUpTo("home") { inclusive = true }

            }
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )
    ModalDrawerSheet() {

        Box(modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .background(
                color = Color.LightGray
            ),
        ){

            Row(
                modifier = Modifier.align(Alignment.CenterStart),
            ){
                KamelImage(
                    resource = asyncPainterResource(profilePic),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(CircleShape)

                )
                if (userData != null)
                Text(text = userData?.firstName!!.trim()+" "+userData?.lastName!!.trim(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(10.dp)
                        .align(Alignment.CenterVertically)

                )
            }
        }


        NavigationDrawerItem(
            label = { Text("Home") },
            selected = false,
            modifier = Modifier.padding(start = 16.dp),
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate("home") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            }
        )

        NavigationDrawerItem(
            label = { Text("Profile") },
            selected = false,
            modifier = Modifier.padding(start = 16.dp),
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate("profile")
            }
        )

        NavigationDrawerItem(
            label = { Text("Logout") },
            selected = false,
            modifier = Modifier.padding(start = 16.dp),
            onClick = {
                scope.launch { drawerState.close() }
                showLogoutDialog = true
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String,navController: NavController,drawerState: DrawerState) {

    val scope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = koinInject()
    val profilePic by settingsViewModel.profilePic.collectAsState()

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch { drawerState.open() }
                }
            ) {
                Image(
                    painter = painterResource(Res.drawable.menu),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate("profile")

                }
            ) {
                KamelImage(
                    resource = asyncPainterResource(profilePic),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .wrapContentWidth()

                )
            }


        }
    )
}

@Composable
fun AppBottomBar(navController: NavController){
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Category,
        BottomNavItem.Profile
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (item.route == BottomNavItem.Home.route) {
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
            )
        }
    }

}

fun getTopBarTitle(route: String?): String {
    return when {
        route == null -> ""

        route.startsWith("home") -> "Home"
        route.startsWith("search") -> "Search"
        route.startsWith("category") -> "Categories"
        route.startsWith("profile") -> "Profile"

        else -> ""
    }
}