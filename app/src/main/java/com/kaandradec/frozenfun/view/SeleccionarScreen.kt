package com.kaandradec.frozenfun.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.navigation.Screen

@Composable
fun SeleccionarScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier =  Modifier.height(16.dp))
        Text(text = "Pantalla Seleccionar")
        Button(onClick = {
            navController.navigate(Screen.Carrito)
        }) {
            Text(text = "Ir Carrito")
        }

        LazyColumn {
            listaDeHelados.forEach { helado ->
                item {
                    Column {
                        Text(text = helado.nombre)
                        Text(text = helado.precio.toString())
                    }
                }
            }
        }
    }

}

fun calcularPrecioTotal(helados: List<Helado>): Float {
    return helados.sumByDouble { it.precio.toDouble() }.toFloat()
}

val listaDeHelados = listOf(
    Helado(1, "Helado de Vainilla", 1.0, "helado_vainilla"),
    Helado(2, "Helado de Chocolate", 3.0, "helado_chocolate"),
    Helado(3, "Helado de Fresa", 3.0, "helado_fresa"),
    Helado(4, "Helado de Limón", 3.0, "helado_limon"),
)

data class Helado(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: String
)