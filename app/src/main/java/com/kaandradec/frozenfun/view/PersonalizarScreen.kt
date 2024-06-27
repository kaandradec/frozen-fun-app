package com.kaandradec.frozenfun.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun PersonalizarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = {
            val item = CartItem(
                id = "2",
                name = "Tulipan",
                price = 10.0,
                quantity = 3
            )
            cartViewModel.addItem(item)
        }) {
            Text(text = "Añadir al carrito")
        }

        Button(onClick = { navController.navigate(Screen.Carrito) }) {
            Text(text = "Ir al carrito")
        }
    }
}