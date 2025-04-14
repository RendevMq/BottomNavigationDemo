package com.rensystem.y02_bottomnavigation

import androidx.compose.ui.graphics.vector.ImageVector
import java.util.Objects

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: Screen  // Usamos Screen en lugar de String
)
