package com.kaandradec.frozenfun.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.kaandradec.frozenfun.R
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
        // Header
//        Header(navController)

        // Search Bar
//        SearchBar()

        // Category Icons
        CategoryIcons()

        // Popular Section
        PopularSection()
    }
}

@Composable
fun Header(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(64.dp)
        )
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .zIndex(1f)  // Esto coloca el icono sobre la imagen
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
    }
}


@Composable
fun CategoryIcons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CategoryIcon(R.drawable.ic_icon_cherry, "Strawberry")
        CategoryIcon(R.drawable.ic_icon_cherry, "Chocolate")
        CategoryIcon(R.drawable.ic_icon_cherry, "Peanut")
        CategoryIcon(R.drawable.ic_icon_cherry, "Mint")
        CategoryIcon(R.drawable.ic_icon_cherry, "Mint")
        CategoryIcon(R.drawable.ic_icon_cherry, "Mint")
        CategoryIcon(R.drawable.ic_icon_cherry, "Mint")
    }
}

@Composable
fun CategoryIcon(iconRes: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = Color.Red,
            modifier = Modifier.size(40.dp)
        )
        Text(text = label, color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
fun PopularSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Popular",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Find your popular ice cream here",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PopularItem(R.drawable.doble, "Strawberry", 52.00)
            PopularItem(R.drawable.banana, "Mint", 52.00)
        }
    }
}

@Composable
fun PopularItem(imageRes: Int, title: String, price: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .width(150.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier.size(100.dp)
        )
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(
            text = "$$price",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF43A047)
        )
    }
}

