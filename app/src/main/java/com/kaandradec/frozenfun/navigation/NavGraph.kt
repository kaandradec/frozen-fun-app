package com.kaandradec.frozenfun.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kaandradec.frozenfun.view.AddPaymentScreen
import com.kaandradec.frozenfun.view.AyudaScreen
import com.kaandradec.frozenfun.view.CarritoScreen
import com.kaandradec.frozenfun.view.ComprobanteScreen
import com.kaandradec.frozenfun.view.DatosScreen
import com.kaandradec.frozenfun.view.DetalleScreen
import com.kaandradec.frozenfun.view.FacturaScreen
import com.kaandradec.frozenfun.view.InfoScreen
import com.kaandradec.frozenfun.view.QRScreen
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
        composable<Screen.Ayuda> {
            AyudaScreen()
        }
        composable<Screen.Info> {
            InfoScreen()
        }
        composable<Screen.Test> {
            TestScreen(navController = navController, cartViewModel = cartViewModel)
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
                nombre = profile.nombre,
                apellido = profile.apellido,
                telefono = profile.telefono,
                email = profile.email,
                cedula = profile.cedula,
            )
        }

        composable<Screen.Comprobante> {
            ComprobanteScreen(navController, cartViewModel)
        }
        composable<Screen.AddPayment> { backStackEntry ->
            // En este caso, el argumento heladoId es opcional
            val profile = backStackEntry.toRoute<Screen.AddPayment>()
            AddPaymentScreen(
                navController,
                nombre = profile.nombre,
                apellido = profile.apellido,
                telefono = profile.telefono,
                email = profile.email,
                cedula = profile.cedula,
            )
        }
        composable<Screen.Datos> {
            DatosScreen(
                navController,
            )
        }
        composable<Screen.QR> { backStackEntry ->
            val profile = backStackEntry.toRoute<Screen.QR>()
            QRScreen(
                navController,
                cartViewModel,
                informacion = profile.informacion,
                porCobrar = profile.porCobrar
            )
        }


    }
}
