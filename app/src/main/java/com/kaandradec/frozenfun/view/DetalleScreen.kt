package com.kaandradec.frozenfun.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    val cartItem = listaMenu.find { it.id == id }

    if (cartItem == null) {
        println("Error: Producto con id $id no encontrado.")
        return
    }

    var quantity by remember { mutableStateOf(0) }
    var selectedGrageas by remember { mutableStateOf(setOf<String>()) }
    var selectedExtras by remember { mutableStateOf(setOf<String>()) }
    var precioTotal by remember { mutableStateOf(cartItem.precio) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item { Spacer(modifier = Modifier.height(getStatusBarHeightDp())) }

        item {
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
                        Text(
                            "+",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
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
                        Text(
                            "-",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
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

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 430.dp, height = 970.dp)
                        .padding(16.dp),
                ){
                    FlavorSelector(cartItem)
                }


                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 430.dp, height = 260.dp)
                        .padding(16.dp),
                ){GrageasSelector(
                    selectedGrageas = selectedGrageas,
                    onGrageaSelected = { gragea ->
                        selectedGrageas = if (selectedGrageas.contains(gragea)) {
                            selectedGrageas - gragea
                        } else {
                            selectedGrageas + gragea
                        }
                        precioTotal = calcularPrecioTotal(cartItem, selectedGrageas, selectedExtras)
                    }
                )
                }

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 430.dp, height = 400.dp)
                        .padding(16.dp),

                    ) {
                    ExtrasSelector(
                        selectedExtras = selectedExtras,
                        onExtraSelected = { extra ->
                            selectedExtras = if (selectedExtras.contains(extra)) {
                                selectedExtras - extra
                            } else {
                                selectedExtras + extra
                            }
                            precioTotal =
                                calcularPrecioTotal(cartItem, selectedGrageas, selectedExtras)

                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total a pagar: $${String.format("%.2f", precioTotal * quantity)}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center, // Centra el texto horizontalmente
                    modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho disponible
                )


                Text(
                    text = cartItem?.descripcion ?: "Descripción no encontrada",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp) // Ajusta el espacio a los lados del botón
                    .padding(vertical = 16.dp)   // Añade un espacio vertical alrededor del botón

            ) {
                Button(
                    onClick = {
                        cartViewModel.addItem(
                            cartItem.copy(
                                quantity = quantity,
                                saboresSeleccionados = cartItem.saboresSeleccionados.toMutableList().apply {
                                    clear()
                                    addAll(selectedGrageas)
                                    addAll(selectedExtras)
                                }
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp) // Altura del botón
                ) {
                    Text(
                        text = "Añadir al carrito",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(150.dp))
            }
        }

    }
}

@Composable
fun FlavorSelector(cartItem: CartItem) {
    val flavors = listOf(
        "Vainilla", "Maracuya", "Yogurt mora", "Chicle", "Galleta",
        "Manjar", "Kinder", "Café", "Guanabana", "Fresa", "Naranjilla",
        "Yogurt durazno", "Chocolate", "Tamarindo", "Mora", "Ron pasas"
    )

    val selectedFlavors = remember { mutableStateListOf<String>() }

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sabores:",
            style = TextStyle(
                fontFamily = FontFamily.SansSerif, // Cambia a la familia de fuentes que desees
                fontWeight = FontWeight.Bold, // Puedes ajustar el peso de la fuente
                fontSize = 22.sp, // Ajusta el tamaño de la fuente según tus necesidades
                color = Color.Black // Puedes ajustar el color del texto
            ),
            modifier = Modifier.padding(start = 8.dp)
        )

        flavors.forEach { flavor ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = flavor,
                    modifier = Modifier.weight(1f)
                        .padding(start = 16.dp)
                )

                Checkbox(
                    checked = selectedFlavors.contains(flavor),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            when (cartItem.nombre) {
                                "Cono simple" -> {
                                    selectedFlavors.clear()
                                    selectedFlavors.add(flavor)
                                }

                                "Cono doble", "Tulipan 2 sabores", "Helado copa doble" -> {
                                    if (selectedFlavors.size < 2) {
                                        selectedFlavors.add(flavor)
                                    }
                                }

                                "Banana Split", "Tulipan extra" -> {
                                    if (selectedFlavors.size < 3) {
                                        selectedFlavors.add(flavor)
                                    }
                                }
                            }
                        } else {
                            selectedFlavors.remove(flavor)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun GrageasSelector(
    selectedGrageas: Set<String>,
    onGrageaSelected: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Grageas:",
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        )
        listOf("Chocolate", "Chispas", "Maní").forEach { gragea ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = gragea,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$${if (gragea == "Chocolate") "0.25" else "0.30"}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Checkbox(
                    checked = selectedGrageas.contains(gragea),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onGrageaSelected(gragea)
                        } else {
                            onGrageaSelected(gragea)
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ExtrasSelector(
    selectedExtras: Set<String>,
    onExtraSelected: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Extras:",
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        )
        listOf("Queso", "Crema", "Barquillos", "Chips de chocolate", "Frutas frescas").forEach { extra ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = extra,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.weight(1f)
                )
                val price = when (extra) {
                    "Queso" -> "$0.60"
                    "Crema" -> "$0.50"
                    "Barquillos" -> "$0.40"
                    "Chips de chocolate" -> "$0.35"
                    "Frutas frescas" -> "$0.30"
                    else -> ""
                }
                Text(
                    text = price,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Checkbox(
                    checked = selectedExtras.contains(extra),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onExtraSelected(extra)
                        } else {
                            onExtraSelected(extra)
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

// Función para calcular el precio total incluyendo grageas y extras seleccionados
private fun calcularPrecioTotal(
    cartItem: CartItem,
    selectedGrageas: Set<String>,
    selectedExtras: Set<String>
): Double {
    var precioTotal = cartItem.precio

    // Sumar el precio de las grageas seleccionadas
    precioTotal += selectedGrageas.sumOf { gragea ->
        if (gragea == "Chocolate") 0.25 else 0.30
    }

    // Sumar el precio de los extras seleccionados
    precioTotal += selectedExtras.sumOf { extra ->
        when (extra) {
            "Queso" -> 0.60
            "Crema" -> 0.50
            "Barquillos" -> 0.40
            "Chips de chocolate" -> 0.35
            "Frutas frescas" -> 0.30
            else -> 0.0
        }
    }
    return precioTotal
}