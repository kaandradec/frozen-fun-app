package com.kaandradec.frozenfun.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.util.getStatusBarHeightDp
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
                ),
                CartItem(
                    4,
                    "Banana Split",
                    1.0,
                    "banana_split",
                    0,
                    sabores = mutableListOf("Banana Slit con 3 sabores de helado"),
                    saboresSeleccionados = mutableListOf(),
                    descripcion = "",
                    image = R.drawable.banana,
                    grageasSeleccionadas = mutableListOf(),
                    extrasSeleccionados = mutableListOf(),
                    tipo = "Banana Split"
                ),
                CartItem(
                    5,
                    "Helado copa doble",
                    2.5,
                    "copa_doble",
                    0,
                    sabores = mutableListOf("Copa de helado con dos sabores de helado"),
                    saboresSeleccionados = mutableListOf(),
                    descripcion = "",
                    image = R.drawable.copa_doble,
                    grageasSeleccionadas = mutableListOf(),
                    extrasSeleccionados = mutableListOf(),
                    tipo = "Copa"
                ),
                CartItem(
                    6,
                    "Tulipan extra",
                    3.0,
                    "tulipan_queso",
                    0,
                    sabores = mutableListOf(""),
                    saboresSeleccionados = mutableListOf(),
                    descripcion = "Tulipan de helado de vainilla y chocolate con queso y crema",
                    image = R.drawable.tuli_sabores_queso,
                    grageasSeleccionadas = mutableListOf(),
                    extrasSeleccionados = mutableListOf(),
                    tipo = "Tulipan",
                )
            )
        )
    }

    var itemCount by remember { mutableStateOf(0) }
    var selectedType by remember { mutableStateOf("Todos") }

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

    var mostrarFiltro by remember { mutableStateOf(false) }

    fun toggleFiltro() {
        mostrarFiltro = !mostrarFiltro
    }

    val statusBarHeight = getStatusBarHeightDp()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Spacer(modifier = Modifier.height(statusBarHeight)) // Espacio para la barra de estado
        PantallaHeader(
            navController = navController,
            cartViewModel = cartViewModel,
            selectedType = selectedType,
            onTipoSeleccionado = { tipo -> selectedType = tipo },
            mostrarFiltro = mostrarFiltro,
            onToggleFiltro = { toggleFiltro() }
        )

        val filteredList =
            if (selectedType == "Todos") listaMenu else listaMenu.filter { it.tipo == selectedType }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { helado ->
                HeladoItem(
                    helado = helado,
                    onIncrease = { heladoId -> increaseQuantity(heladoId) },
                    onDecrease = { heladoId -> decreaseQuantity(heladoId) },
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // Permitir desplazamiento horizontal
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Todos", "Cono", "Tulipan", "Copa", "Banana Split").forEach { tipo ->
                Button(
                    onClick = { selectedType = tipo },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == tipo) MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = tipo)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            val filteredList =
                if (selectedType == "Todos") listaMenu else listaMenu.filter { it.tipo == selectedType }

            items(filteredList) { helado ->
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
    cartViewModel: CartViewModel,
    selectedType: String,
    onTipoSeleccionado: (String) -> Unit,
    mostrarFiltro: Boolean, // Pasa el estado de visibilidad del filtro
    onToggleFiltro: () -> Unit // Pasa la función de cambio de estado
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { onToggleFiltro() }, // Toggle para cambiar el estado de visibilidad
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtro",
                )
            }
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

    if (mostrarFiltro) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // Permitir desplazamiento horizontal
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Todos", "Cono", "Tulipan", "Copa", "Banana Split").forEach { tipo ->
                Button(
                    onClick = { onTipoSeleccionado(tipo) }, // Actualiza el tipo seleccionado
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == tipo) MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = tipo)
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    tipo: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
    val contentColor =
        if (isSelected) Color.White else MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = tipo)
    }
}
