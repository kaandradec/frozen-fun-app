package com.kaandradec.frozenfun.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaandradec.frozenfun.R

@Composable
fun AyudaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ayuda",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        ToggleableContent(title = "¿Cómo Comprar?") {
            SeccionAyudaComoComprar()
        }
        ToggleableContent(title = "¿Cómo Comprar para Consumidor Final?") {
            SeccionAyudaCompraParaConsumidorFinal()
        }
        ToggleableContent(title = "¿Cómo Comprar con Tarjeta?") {
            SeccionAyudaCompraConTarjeta()
        }
    }
}

@Composable
fun ToggleableContent(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(8.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            contentDescription = if (expanded) "Collapse" else "Expand",
            modifier = Modifier.size(32.dp)
        )
    }
    AnimatedVisibility(visible = expanded, modifier = Modifier.fillMaxWidth()) {
        content()
    }
}

@Composable
fun SeccionAyudaComoComprar() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item { ItemAyuda(orden = 1, R.drawable.compra_1, "Selecciona los productos") }
        item { ItemAyuda(orden = 2, R.drawable.compra_2, "Agrega al carrito") }
        item { ItemAyuda(orden = 3, R.drawable.compra_3, "Realiza el pago") }
    }
}

@Composable
fun SeccionAyudaCompraParaConsumidorFinal() {

    LazyColumn {
        item { ItemAyuda(orden = 1, R.drawable.consumidor_final_2, "Elige opción de consumidor final") }
        item { ItemAyuda(orden = 2, R.drawable.consumidor_final3, "Descarga tu comprobante") }
        item { ItemAyuda(orden = 3, R.drawable.qr_consumidor, "Recibir el pedidio con el código QR y pagar en efectivo") }
    }
}

@Composable
fun SeccionAyudaCompraConTarjeta() {

    LazyColumn {
        item { ItemAyuda(orden = 1, R.drawable.comprar_tarjeta1, "Selecciona tarjeta como método de pago") }
        item { ItemAyuda(orden = 2, R.drawable.compra_tarjeta2, "Ingresa los datos de tu tarjeta") }
        item { ItemAyuda(orden = 3, R.drawable.compra_tarjeta3, "Descarga tu comprobante") }
        item { ItemAyuda(orden = 4, R.drawable.qr_tarjets, "Recibir el pedidio con el código QR") }
    }
}

@Composable
fun ItemAyuda(orden: Int, imageResId: Int, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "$orden",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        val image: Painter = painterResource(id = imageResId)
        Text(
            text = description,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 16.sp,
        )
        Image(
            painter = image,
            contentDescription = description,
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Mantiene el padding dentro del Box
        )
    }
}