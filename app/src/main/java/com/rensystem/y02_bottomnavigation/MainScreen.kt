package com.rensystem.y02_bottomnavigation

import android.util.Log
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rensystem.y02_bottomnavigation.Screen.*
import com.rensystem.y02_bottomnavigation.pages.HomePage
import com.rensystem.y02_bottomnavigation.pages.NotificationPage
import com.rensystem.y02_bottomnavigation.pages.SettingsPage

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()  // Usamos un NavController para la navegación
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home, Home),
        NavItem("Notification", Icons.Default.Notifications, Notification),
        NavItem("Settings", Icons.Default.Settings, Settings)
    )

    // Obtenemos el estado actual de la pila de navegación
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Log de la ruta actual
    LaunchedEffect(currentBackStackEntry) {
        val currentRoute = currentBackStackEntry?.destination?.route?.substringAfterLast(".")
        if (currentRoute != null) {
            Log.d("Navigation Log", "Ruta actual: $currentRoute")
            Log.d("Navigation Log", "Ruta actual: ${Home.toString()}")
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
            ) {
                navItemList.forEachIndexed { index, navItem ->

                    NavigationBarItem(
                        selected = getRouteFromBackStackEntry(currentBackStackEntry) == navItem.route.toString(),
                        onClick = {
//                            when (index) {
//                                0 -> navController.navigate(Home)  // Navegar a Home
//                                1 -> navController.navigate(Notification)  // Navegar a Notification
//                                2 -> navController.navigate(Settings)  // Navegar a Settings
//                            }
                            // Solo realizar la navegación si no estamos en la misma pantalla
                            val currentRoute = getRouteFromBackStackEntry(currentBackStackEntry)
                            if (currentRoute != navItem.route.toString()) {
                                navController.navigate(navItem.route) {
                                    launchSingleTop = true
                                    // Usamos popUpTo para evitar duplicados en la pila
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            }
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)  // Consume las insets del scaffold
                .padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController  // Pasa el NavController a ContentScreen
        )
    }
}

fun getRouteFromBackStackEntry(currentBackStackEntry: NavBackStackEntry?): String =
    currentBackStackEntry?.destination?.route?.substringAfterLast(".").orEmpty()



@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    // Usamos NavController para la navegación dentro de ContentScreen
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { HomePage(modifier) }
        composable<Notification> { NotificationPage(modifier) }
        composable<Settings> { SettingsPage(modifier) }
    }
}

