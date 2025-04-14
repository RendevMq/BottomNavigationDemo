package com.rensystem.y02_bottomnavigation
import kotlinx.serialization.Serializable

// Definimos los objetos de las pantallas
//Data objetc para que imprima como "Home" y no "com.rensystem.y02_bottomnavigation.Home@d33887"
//@Serializable
//data object Home
//
//@Serializable
//data object Notification
//
//@Serializable
//data object Settings

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object Notification : Screen()

    @Serializable
    data object Settings : Screen()
}

