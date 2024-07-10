package com.kaandradec.frozenfun.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.MainActivity
import com.kaandradec.frozenfun.getStatusBarHeightDp
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@SuppressLint("InternalInsetResource", "DiscouragedApi")
@Composable
fun CarritoScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val cartItems = cartViewModel.cartItems
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Obtenemos la altura de la barra de estado
    val statusBarHeightDp = getStatusBarHeightDp()

    Column {
        Spacer(modifier = Modifier.height(statusBarHeightDp))
        CartHeader(onBackClick = { navController.popBackStack() }, cartViewModel = cartViewModel)
        CartItemList(
            items = cartItems,
            onIncrement = { cartViewModel.addHelado(it) },
            onDecrement = { cartViewModel.removeHelado(it) }
        )
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
//            Text(text = item.ingredients, style = MaterialTheme.typography.body2, color = Color.Gray)
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

@Composable
@Preview
fun test() {

}