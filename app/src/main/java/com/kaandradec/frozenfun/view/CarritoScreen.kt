package com.kaandradec.frozenfun.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.MainActivity
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun CarritoScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val cartItems = cartViewModel.cartItems
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "        Pantalla Carrito",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { cartViewModel.clearCart() }, // Función para limpiar el carrito
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar todo"
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cartItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = { /* Handle card click if needed */ }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Imagen del helado
                        val imagePainter = painterResource(id = getResourceId(item.imagen))
                        Image(
                            painter = imagePainter,
                            contentDescription = "Imagen de ${item.nombre}",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 16.dp)
                        )

                        // Detalles del helado
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "Nombre: ${item.nombre}")
                            Text(text = "Precio: ${item.precio}")
                            Text(text = "Cantidad: ${item.quantity}")

                            // Botón para eliminar
                            Button(
                                onClick = { cartViewModel.removeHelado(item) },
                                modifier = Modifier
                                    .align(Alignment.End)
                            ) {
                                Text(text = "Eliminar")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val totalPrice = cartItems.sumOf { it.precio * it.quantity }
        Text(text = "Total a Pagar: $$totalPrice", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                navController.navigate(Screen.Ajustes)
            },
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
            ) {
                Text(text = "Pagar helados", fontSize = 17.sp)
            }

            Button(
                onClick = {
                    navController.navigate(Screen.Seleccionar)
                },
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
            ) {
                Text(text = "Regresar a menú",fontSize = 17.sp)
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}