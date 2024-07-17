package com.kaandradec.frozenfun.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@SuppressLint("InternalInsetResource", "DiscouragedApi")
@Composable
fun CarritoScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel

) {
    val cartItems = cartViewModel.cartItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            CartHeader(
                onBackClick = { navController.popBackStack() },
                cartViewModel = cartViewModel
            )
            CartItemList(
                items = cartItems,
                onIncrement = { cartViewModel.addHelado(it) },
                onDecrement = { cartViewModel.removeHelado(it) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        val totalPrice = cartItems.sumOf { it.precio * it.quantity }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total a Pagar: $$totalPrice", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Datos)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Pagar helados")
            }
        }
    }
}


@Composable
fun CartHeader(
    onBackClick: () -> Unit,
    cartViewModel: CartViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Tu carrito",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { cartViewModel.clearCart() }
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp, 4.dp)
        ) {
            Text(
                text = "Borrar todo",
                color = MaterialTheme.colorScheme.error
            )
            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "Eliminar todo",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(0.dp)
            )
        }


    }
}

@Composable
fun CartItemRow(item: CartItem, onIncrement: (CartItem) -> Unit, onDecrement: (CartItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = item.nombre, fontWeight = FontWeight.Bold)

            Text(buildAnnotatedString {
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("Sabores: ")
                Spacer(modifier = Modifier.height(8.dp))
                pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
                append(item.saboresSeleccionados.joinToString(", "))
            }, style = TextStyle.Default)


            Text(buildAnnotatedString {
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("Grageas: ")
                Spacer(modifier = Modifier.height(8.dp))
                pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
                append(item.grageasSeleccionadas.joinToString(", "))
            }, style = TextStyle.Default)

            Text(buildAnnotatedString {
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("Extras: ")
                Spacer(modifier = Modifier.height(8.dp))
                pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
                append(item.extrasSeleccionados.joinToString(", "))
            }, style = TextStyle.Default)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${item.precio}", fontWeight = FontWeight.Bold)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onDecrement(item) }) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease Quantity")
            }
            Text(text = item.quantity.toString())
            IconButton(onClick = { onIncrement(item) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase Quantity")
            }
        }
    }
}

@Composable
fun CartItemList(
    items: List<CartItem>,
    onIncrement: (CartItem) -> Unit,
    onDecrement: (CartItem) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            CartItemRow(item = item, onIncrement = onIncrement, onDecrement = onDecrement)
        }
    }
}