package com.kaandradec.frozenfun.view

import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kaandradec.frozenfun.data.datos
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun DetalleScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    id: Int
) {


    val cartItem = datos.find { it.id == id }

    if (cartItem == null) {
        println("Error: Producto con id $id no encontrado.")
        return
    }

    var quantity by remember { mutableStateOf(1) }
    var selectedGrageas by remember { mutableStateOf(setOf<String>()) }
    var selectedExtras by remember { mutableStateOf(setOf<String>()) }
    var selectedFlavors = remember { mutableStateListOf<String>() }
    var precioTotal by remember { mutableStateOf(cartItem.precio) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {


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
                // Iconos de regresar y del carrito con BadgedBox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween, // Asegura que los íconos estén en extremos opuestos
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Ícono de regresar
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    // Ícono del carrito con BadgedBox
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
                                        navController.context,
                                        "No hay productos en el carrito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
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

                    Button(
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
                            fontWeight = FontWeight.SemiBold
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


                OutlinedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier.height(IntrinsicSize.Max)
                ) {
                    FlavorSelector(
                        cartItem = cartItem,
                        selectedFlavors = selectedFlavors,
                        onFlavorSelected = { flavor ->
                            if (!selectedFlavors.contains(flavor)) {
                                selectedFlavors.add(flavor)
                            } else {
                                selectedFlavors.remove(flavor)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier.height(IntrinsicSize.Max)
                ) {
                    GrageasSelector(
                        selectedGrageas = selectedGrageas,
                        onGrageaSelected = { gragea ->
                            selectedGrageas = if (selectedGrageas.contains(gragea)) {
                                selectedGrageas - gragea
                            } else {
                                selectedGrageas + gragea
                            }
                            precioTotal =
                                calcularPrecioTotal(cartItem, selectedGrageas, selectedExtras)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier.height(IntrinsicSize.Max)

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
                    modifier = Modifier.fillMaxWidth() // Ocupa t0do el ancho disponible
                )


                Text(
                    text = cartItem.descripcion,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
                        val nuevoHelado = CartItem(
                            id = cartItem.id,
                            nombre = cartItem.nombre,
                            precio = precioTotal,
                            descripcion = cartItem.descripcion,
                            image = cartItem.image,
                            quantity = quantity,
                            sabores = cartItem.sabores,
                            saboresSeleccionados = selectedFlavors.toMutableList(),
                            /*.apply {
                                clear()
                                addAll(selectedGrageas)
                                addAll(selectedExtras)
                            },*/
                            grageasSeleccionadas = selectedGrageas.toMutableList(),
                            extrasSeleccionados = selectedExtras.toMutableList(),
                            tipo = cartItem.tipo,
                            imagen = cartItem.imagen
                        )
                        cartViewModel.addItem(
                            nuevoHelado
                        )

                        // Mostrar mensaje de "Producto Añadido"
                        Toast.makeText(
                            navController.context,
                            "Producto Añadido",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                ) {
                    Text(
                        text = "Añadir al carrito",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(64.dp))
            }
        }

    }
}

@Composable
fun FlavorSelector(
    selectedFlavors: MutableList<String>,
    onFlavorSelected: (String) -> Unit,
    cartItem: CartItem
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Sabores:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(start = 5.dp, top = 8.dp)
                .clickable { expanded = !expanded }
        )

        if (expanded) {
            listOf(
                "Vainilla", "Maracuya", "Yogurt mora", "Chicle", "Galleta",
                "Manjar", "Kinder", "Café", "Guanabana", "Fresa", "Naranjilla",
                "Yogurt durazno", "Chocolate", "Tamarindo", "Mora", "Ron pasas"
            ).forEach { flavor ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = flavor,
                        modifier = Modifier
                            .weight(1f)
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
        } else {
            Spacer(modifier = Modifier.height(0.dp)) // Agrega un espacio de altura cero cuando está colapsado
        }
    }
}

@Composable
fun GrageasSelector(
    selectedGrageas: Set<String>,
    onGrageaSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Grageas:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { expanded = !expanded }
        )

        if (expanded) {
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
        } else {
            Spacer(modifier = Modifier.height(0.dp)) // Agrega un espacio de altura cero cuando está colapsado
        }
    }
}

@Composable
fun ExtrasSelector(
    selectedExtras: Set<String>,
    onExtraSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(
            text = "Extras:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { expanded = !expanded }
        )

        if (expanded) {
            listOf(
                "Queso",
                "Crema",
                "Barquillos",
                "Chips de chocolate",
                "Frutas frescas"
            ).forEach { extra ->
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
        } else {
            Spacer(modifier = Modifier.height(0.dp)) // Agrega un espacio de altura cero cuando está colapsado
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