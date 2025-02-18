package com.kaandradec.frozenfun.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun TestScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCE4EC)) // Fondo similar al de la imagen
    ) {
        Text(text = "TEST", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}