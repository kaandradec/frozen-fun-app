package com.kaandradec.frozenfun.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel


@Composable
fun SeleccionarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    var listaMenu by remember { mutableStateOf(
        listOf(
            CartItem(1, "Cono simple", 1.0, "cono_simple", 0),
            CartItem(2, "Cono doble", 3.0, "cono_doble", 0),
            CartItem(3, "Tulipan 2 sabores", 3.0, "tulipan", 0),
            CartItem(4, "Banana Split", 1.0, "banana_split", 0),
            CartItem(5, "Helado copa doble", 2.5, "copa_doble", 0),
            CartItem(3, "Tulipan 2 sab. + queso + crema", 3.0, "tulipan_queso", 0)
           )
    )}

    var itemCount by remember { mutableStateOf(0) }

    fun increaseQuantity(heladoId: Int) {
        listaMenu = listaMenu.map { helado ->
            if (helado.id == heladoId) {
                val updatedHelado = helado.copy(quantity = helado.quantity + 1)
                cartViewModel.addHelado(updatedHelado)
                itemCount++
                updatedHelado
            } else {
                helado
            }
        }
    }

    fun decreaseQuantity(heladoId: Int) {
        listaMenu = listaMenu.map { helado ->
            if (helado.id == heladoId && helado.quantity > 0) {
                val updatedHelado = helado.copy(quantity = helado.quantity - 1)
                cartViewModel.removeHelado(updatedHelado)
                itemCount--
                updatedHelado
            } else {
                helado
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            Text(text = "Pantalla Seleccionar", fontSize = 24.sp)
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

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaMenu) { helado ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    onClick = { /* Handle card click if needed */ }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Imagen del helado
                        val imagePainter = painterResource(id = getResourceId(helado.imagen))
                        Image(
                            painter = imagePainter,
                            contentDescription = "Imagen de ${helado.nombre}",
                            modifier = Modifier
                                .size(125.dp)
                                .padding(end = 16.dp)
                        )

                        // Detalles del helado
                        Column(

                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)

                        ) {
                            // Nombre del helado
                            Text(text = "Nombre: ${helado.nombre}")

                            // Precio del helado
                            Text(text = "Precio: $${helado.precio}")

                            // Botones de cantidad
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Button(onClick = { decreaseQuantity(helado.id) }) {
                                    Text(text = "-")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = helado.quantity.toString())

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(onClick = { increaseQuantity(helado.id) }) {
                                    Text(text = "+")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

// Función para obtener el ID del recurso de imagen
fun getResourceId(imageName: String): Int {
    return when (imageName) {
        "cono_simple" -> com.kaandradec.frozenfun.R.drawable.images
        "cono_doble" -> com.kaandradec.frozenfun.R.drawable.doble
        "tulipan" -> com.kaandradec.frozenfun.R.drawable.tuli_sabores
        "banana_split" -> com.kaandradec.frozenfun.R.drawable.banana
        "copa_doble" -> com.kaandradec.frozenfun.R.drawable.copa_doble
        "tulipan_queso" -> com.kaandradec.frozenfun.R.drawable.tuli_sabores_queso
        else -> com.kaandradec.frozenfun.R.drawable.images // Fallback
    }
}
