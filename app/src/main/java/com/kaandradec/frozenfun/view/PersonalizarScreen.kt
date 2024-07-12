package com.kaandradec.frozenfun.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun PersonalizarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val helados = listOf(
        CartItem(
            1,
            "Cono simple",
            1.0,
            "cono_simple",
            0,
            descripcion = "Cono simple con 1 sabor de helado",
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf(),
            image = R.drawable.images,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Cono"
        ),
        CartItem(
            2,
            "Cono doble",
            3.0,
            "cono_doble",
            0,
            descripcion = "  Cono doble de helado de vainilla y chocolate",
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf(),
            image = R.drawable.doble,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Cono"
        ),
        CartItem(
            3,
            "Tulipan 2 sabores",
            3.0,
            "tulipan",
            0,
            descripcion = "     Tulipan de helado de vainilla y chocolate",
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf(),
            image = R.drawable.tuli_sabores,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Tulipan"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(90.dp))

            Text(text = "  Pantalla Personalizar", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            BadgedBox(
                badge = {
                    if (cartViewModel.cartItems.isNotEmpty()) {
                        Badge(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ) {
                            Text("${cartViewModel.cartItems.sumBy { it.quantity }}")
                        }
                    }
                },
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Carrito)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Ir al carrito"
                )
            }
        }

        helados.forEach { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Nombre: ${item.nombre}", fontSize = 20.sp)
                Text(text = "Precio: ${item.precio}", fontSize = 20.sp)
                Text(text = "Cantidad: ${item.quantity}", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            val newItem = item.copy(quantity = item.quantity + 1)
                            cartViewModel.addItem(newItem)
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(text = "Añadir al carrito")
                    }

                    Button(
                        onClick = { navController.navigate(Screen.Carrito) },
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(text = "Ir al carrito")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}