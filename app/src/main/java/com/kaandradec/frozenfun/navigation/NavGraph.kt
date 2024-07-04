package com.kaandradec.frozenfun.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kaandradec.frozenfun.view.AjustesScreen
import com.kaandradec.frozenfun.view.CarritoScreen
import com.kaandradec.frozenfun.view.PersonalizarScreen
import com.kaandradec.frozenfun.view.SeleccionarScreen
import com.kaandradec.frozenfun.viewmodel.CartViewModel


@Composable
fun SetupGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    startDestination: Screen = Screen.Seleccionar
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Seleccionar> {
            SeleccionarScreen(navController, cartViewModel)
        }
        composable<Screen.Personalizar> {
            PersonalizarScreen(navController, cartViewModel)
        }
        composable<Screen.Ajustes> {
            AjustesScreen()
        }
        composable<Screen.Carrito> { backStackEntry ->
            CarritoScreen(navController, cartViewModel)
        }
    }
}
