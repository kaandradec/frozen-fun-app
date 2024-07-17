package com.kaandradec.frozenfun.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.data.datos
import com.kaandradec.frozenfun.data.tagsList
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun SeleccionarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {

    val context = LocalContext.current
    var listaMenu by remember {
        mutableStateOf(datos)
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

    var mostrarFiltro by remember { mutableStateOf(true) }

    fun toggleFiltro() {
        mostrarFiltro = !mostrarFiltro
    }

//    val statusBarHeight = getStatusBarHeightDp()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f), // Ajusta la opacidad al 50%
            contentScale = ContentScale.Crop // Asegura que la imagen cubra todo el espacio disponible
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {

            PantallaHeader(
                navController = navController,
                cartViewModel = cartViewModel,
                selectedType = selectedType,
                onTipoSeleccionado = { tipo -> selectedType = tipo },
                mostrarFiltro = mostrarFiltro,
                onToggleFiltro = { toggleFiltro() },
                context = context
            )
            Image(
                painter = painterResource(id = R.drawable.logonombre),
                contentDescription = "Nombre de la tienda",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))


            if (mostrarFiltro) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()), // Permitir desplazamiento horizontal
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Todos", "Cono", "Tulipan", "Copa", "Banana Split").forEach { tipo ->
                        Button(
                            onClick = { selectedType = tipo }, // Actualiza el tipo seleccionado
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedType == tipo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceBright
                            ),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = tipo,
                                color = if (selectedType == tipo) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                CartItemsRow()

            }
            Spacer(modifier = Modifier.height(8.dp))

            val filteredList =
                if (selectedType == "Todos") listaMenu else listaMenu.filter { it.tipo == selectedType }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    ) // Ajusta el radio del redondeo según necesites
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp, 16.dp, 20.dp, 0.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Usar `key` para mejorar el rendimiento en listas que cambian
                    items(filteredList, key = { helado -> helado.id }) { helado ->
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
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
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

            if (helado.esDeOtraMarca) {
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

            } else {
                // Boton con icono "Personalizar"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    IconButton(
                        onClick = { navController.navigate(Screen.Detalle(helado.id)) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit, // Asumiendo que tienes un icono de edición
                            contentDescription = "Personalizar",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Personalizar",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold

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
    onToggleFiltro: () -> Unit, // Pasa la función de cambio de estado
    context: Context

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { onToggleFiltro() }, // Toggle para cambiar el estado de visibilidad
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {

            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Mostrar Filtros",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
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
                }
            ) {
                IconButton(
                    onClick = {
                        if (cartViewModel.cartItems.isNotEmpty()) {
                            navController.navigate(Screen.Carrito)
                        } else {
                            Toast.makeText(
                                context,
                                "No hay productos en el carrito",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Ir al carrito",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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

@Composable
fun CartItemsRow() {
    // Simulando un desplazamiento infinito repitiendo los elementos de la lista
    val infiniteList = remember { List(100) { tagsList[it % tagsList.size] } }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(infiniteList) { index, tagItem ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .size(width = 140.dp, height = 150.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = tagItem.imageResId),
                        contentDescription = tagItem.tag,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = tagItem.tag,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}