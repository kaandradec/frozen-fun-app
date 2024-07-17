package com.kaandradec.frozenfun.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.viewmodel.CartViewModel

@Composable
fun QRScreen(
    navHostController: NavHostController,
    cartViewModel: CartViewModel,
    porCobrar: Boolean,
    informacion: String,
) {
    val textToQR = informacion // Este es el texto que se convertirá en QR.
    val msg = if (porCobrar) "POR COBRAR" else "PAGADO"
    val informacionQR = "$msg\n\n $informacion\n\n CODIGO DE PEDIDO:${generarCodigoPedido()}"
    val qrBitmap = generateQRCodeBitmap(informacionQR)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = msg,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (porCobrar) Color.Red else Color.Green
        )
        Spacer(modifier = Modifier.height(20.dp))
        qrBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.size(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                cartViewModel.clearCart() // Limpiar el carrito
                navHostController.navigate(Screen.Seleccionar)
            }
        ) {
            Text("Volver a comprar")
        }
    }
}

fun generateQRCodeBitmap(text: String): Bitmap? {
    return try {
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 200, 200)
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}

fun generarCodigoPedido(): String {
    val length = 6
    val charset = "0123456789"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}
