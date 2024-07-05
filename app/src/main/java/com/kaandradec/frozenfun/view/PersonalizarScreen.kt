package com.kaandradec.frozenfun.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel


@Composable
fun PersonalizarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val helados = listOf(
        CartItem(1, "Helado de Vainilla", 1.0, "helado_vainilla", 0),
        CartItem(2, "Helado de Chocolate", 3.0, "helado_chocolate", 0),
        CartItem(3, "Helado de Fresa", 3.0, "helado_fresa", 0),
        CartItem(4, "Helado de Limón", 3.0, "helado_limon", 0),
        CartItem(5, "Helado de Naranja", 2.5, "helado_naranja", 0),
        CartItem(6, "Helado de Maracuya", 4.0, "helado_maracuya", 0),
        CartItem(7, "Helado de Mango", 2.5, "helado_mango", 0),
        CartItem(8, "Helado de Agua", 1.5, "helado_agua", 0),
        CartItem(9, "Helado de Café", 2.5, "helado_cafe", 0),
        CartItem(10, "Helado de Dulce de Leche", 3.5, "helado_dulce_de_leche", 0),
        CartItem(11, "Helado de Dulce de Leche con Leche", 4.0, "helado_Dulce_de_Leche", 0)
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