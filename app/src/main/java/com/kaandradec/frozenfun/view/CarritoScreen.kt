package com.kaandradec.frozenfun.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun CarritoScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    // JETPACK COMPOSE
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Carrito de helados")

        LazyColumn {
            cartViewModel.getAllItems().forEachIndexed() { index, item ->
                item {
                    Text(text = "Helado ${index + 1}: ${item.name} - ${item.price}")
                }
            }
        }
        Button(onClick = {
            navController.navigate(Screen.Ajustes)
        }) {
            Text(text = "Pagar helados")
        }
    }
}

