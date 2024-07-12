package com.kaandradec.frozenfun.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kaandradec.frozenfun.view.AjustesScreen
import com.kaandradec.frozenfun.view.CarritoScreen
import com.kaandradec.frozenfun.view.DetalleScreen
import com.kaandradec.frozenfun.view.FacturaScreen
import com.kaandradec.frozenfun.view.PersonalizarScreen
import com.kaandradec.frozenfun.view.SeleccionarScreen
import com.kaandradec.frozenfun.view.TestScreen
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
        composable<Screen.Detalle> { backStackEntry ->
            // En este caso, el argumento heladoId es opcional
            val profile = backStackEntry.toRoute<Screen.Detalle>()
            DetalleScreen(navController, cartViewModel, id = profile.id)
        }
        composable<Screen.Personalizar> {
            PersonalizarScreen(navController, cartViewModel)
        }
        composable<Screen.Ajustes> {
            AjustesScreen()
        }
        composable<Screen.Test> {
            TestScreen(navController, cartViewModel)
        }
        composable<Screen.Carrito> { backStackEntry ->
            CarritoScreen(navController, cartViewModel)
        }
        composable<Screen.Factura> { backStackEntry ->
            // En este caso, el argumento heladoId es opcional
            val profile = backStackEntry.toRoute<Screen.Factura>()
            FacturaScreen(
                navController,
                cartViewModel,
                cedula = profile.cedula,
                correo = profile.correo
            )
        }

    }
}
