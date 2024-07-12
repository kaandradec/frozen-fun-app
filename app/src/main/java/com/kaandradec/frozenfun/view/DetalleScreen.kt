package com.kaandradec.frozenfun.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.getStatusBarHeightDp
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun DetalleScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    id: Int
) {
    val listaMenu by remember {
        mutableStateOf(
            listOf(
                CartItem(
                    1,
                    "Cono simple",
                    1.0,
                    "cono_simple",
                    0,
                    descripcion = "Cono simple de helado de vainilla",
                    sabores = listOf("Vainilla"),
                    image = com.kaandradec.frozenfun.R.drawable.images
                ),
                CartItem(
                    2,
                    "Cono doble",
                    3.0,
                    "cono_doble",
                    0,
                    descripcion = "Cono doble de helado de vainilla y chocolate",
                    sabores = listOf("Vainilla", "Chocolate"),
                    image = com.kaandradec.frozenfun.R.drawable.doble
                ),
                CartItem(
                    3,
                    "Tulipan 2 sabores",
                    3.0,
                    "tulipan",
                    0,
                    descripcion = "Tulipan de helado de vainilla y chocolate",
                    sabores = listOf("Vainilla", "Chocolate"),
                    image = com.kaandradec.frozenfun.R.drawable.tuli_sabores
                ),
                CartItem(
                    4,
                    "Banana Split",
                    1.0,
                    "banana_split",
                    0,
                    sabores = listOf("Vainilla", "Chocolate"),
                    descripcion = "Banana Split de helado de vainilla y chocolate",
                    image = com.kaandradec.frozenfun.R.drawable.banana
                ),
                CartItem(
                    5,
                    "Helado copa doble",
                    2.5,
                    "copa_doble",
                    0,
                    sabores = listOf("Vainilla", "Chocolate"),
                    descripcion = "Copa doble de helado de vainilla y chocolate",
                    image = com.kaandradec.frozenfun.R.drawable.copa_doble
                ),
                CartItem(
                    3,
                    "Tulipan extra",
                    3.0,
                    "tulipan_queso",
                    0,
                    sabores = listOf("Vainilla", "Chocolate", "Queso", "Crema"),
                    descripcion = "Tulipan de helado de vainilla y chocolate con queso y crema",
                    image = com.kaandradec.frozenfun.R.drawable.tuli_sabores_queso
                )
            )
        )
    }

    val cartItem = listaMenu.find { it.id == id }
    if (cartItem == null) {
        println("Error: Producto con id $id no encontrado.")
        return
    }

    var quantity by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier =   Modifier.height(getStatusBarHeightDp()))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)  // Esto asegura que la imagen sea un cuadrado
                .clip(
                    RoundedCornerShape(
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp
                    )
                )  // Redondea solo las esquinas inferiores
        ) {
            Image(
                painter = painterResource(
                    id = cartItem?.image ?: R.drawable.images
                ), // TODO Falta definir una imagen por defecto
                contentDescription = "Imagen del helado",
                contentScale = ContentScale.Crop,  // Esto asegura que la imagen haga "fill"
                modifier = Modifier.fillMaxSize()
            )
            // Icono para regresar
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .zIndex(1f)  // Esto coloca el icono sobre la imagen
                    .align(Alignment.TopStart)  // Esto coloca el icono en la esquina superior izquierda
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            // Column que contiene los botones de incrementar y decrementar la cantidad
            Column(
                modifier = Modifier
                    .padding(16.dp)
//                    .offset(y = 52.dp)  // Ajuste para sobresalir hacia abajo
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .align(Alignment.BottomEnd)  // Esto coloca la Column en la parte inferior del Box
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                IconButton(
                    onClick = { quantity++ },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text("+", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = quantity.toString().padStart(2, '0'),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                IconButton(
                    onClick = { if (quantity > 0) quantity-- },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text("-", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(32.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = cartItem?.nombre ?: "Helado no encontrado",
                lineHeight = 32.sp,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = cartItem?.sabores?.joinToString(", ") ?: "Sabores no encontrados",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "$${cartItem?.precio ?: 0.0}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = cartItem?.descripcion ?: "Descripción no encontrada",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        ElevatedButton(
            onClick = { /* Do something! */ },
            modifier = Modifier
                .fillMaxWidth()  // Ocupa t0do el ancho disponible
                .padding(32.dp)  // Agrega un padding alrededor del botón

        ) {
            Text(
                text = "Añadir al carrito",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

