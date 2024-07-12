package com.kaandradec.frozenfun.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ElevatedCard
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
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.getStatusBarHeightDp
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun SeleccionarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    var listaMenu by remember {
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

    val statusBarHeight = getStatusBarHeightDp()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(statusBarHeight))
        PantallaHeader(navController = navController, cartViewModel = cartViewModel)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaMenu) { helado ->
                HeladoItem(
                    helado = helado,
                    onIncrease = { heladoId -> increaseQuantity(heladoId) },
                    onDecrease = { heladoId -> decreaseQuantity(heladoId) },
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun HeladoItem(
    helado: CartItem,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
    navController: NavHostController
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { navController.navigate(Screen.Detalle(helado.id)) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .width(144.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)  // Esto asegura que la imagen sea un cuadrado
                    .clip(
                        RoundedCornerShape(16.dp)
                    )  // Redondea solo las esquinas inferiores
            ) {
                // Imagen del helado
                Image(
                    painter = painterResource(id = helado.image),
                    contentDescription = "Ice Cream",
                    contentScale = ContentScale.Crop,  // Esto asegura que la imagen haga "fill"
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Detalles del helado
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = helado.nombre,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
                Text(
                    text = "$${helado.precio}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .wrapContentWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { onIncrease(helado.id) },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Aumentar Cantidad",
                        tint = Color.White
                    )
                }

                Text(
                    text = helado.quantity.toString().padStart(2, '0'),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                IconButton(
                    onClick = { onDecrease(helado.id) },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Disminuir Cantidad",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun PantallaHeader(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Elige tus helados",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
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
}