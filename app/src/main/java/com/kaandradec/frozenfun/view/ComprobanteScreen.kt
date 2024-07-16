package com.kaandradec.frozenfun.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.util.getStatusBarHeightDp
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.view.composables.PdfViewer
import com.kaandradec.frozenfun.viewmodel.CartViewModel
import com.shockwave.pdfium.PdfiumCore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@Composable
fun ComprobanteScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
) {
    val context = LocalContext.current

    val lista = cartViewModel.cartItems

    createComprobanteConsumidorFinalPdf(context, lista)

    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "comprobante.pdf")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
//        Spacer(modifier = Modifier.height(getStatusBarHeightDp()))
        ComprobanteHeader(
            onBackClick = { navController.popBackStack() },
            file = file,
            context = context
        )
        Text(
            text = "GRACIAS POR SU COMPRA",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Realice el pago en la ventanilla de caja",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))

        PdfViewer(pdfFile = file)
        Button(
            onClick = { moveComprobanteFileToDownloads(context, file) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Descargar archivo")
        }
    }
}


fun createComprobanteConsumidorFinalPdf(context: Context, items: List<CartItem>) {
    val document = Document()
    try {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "comprobante.pdf"
        )
        PdfWriter.getInstance(document, FileOutputStream(file))
        document.open()

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = Image.getInstance(stream.toByteArray())
        image.scaleToFit(100f, 100f)
        document.add(image)

        val titleFont = Font(Font.FontFamily.HELVETICA, 22f, Font.BOLD)
        val font = Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val invoiceNumber = generateInvoiceNumber()

        document.add(Paragraph("Comprobante de Consumidor Final", titleFont))
        document.add(Paragraph("Fecha: $date", font))
        document.add(Paragraph("Número de Comprobante: $invoiceNumber", font))

        // Agregar más detalles del consumidor aquí si es necesario

        val table = PdfPTable(5)
        table.widthPercentage = 100f
        val cellFont = Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
        val cellBackgroundColor = BaseColor(200, 200, 200)

        table.addCell(PdfPCell(Phrase("Código", cellFont)).apply {
            backgroundColor = cellBackgroundColor
        })
        table.addCell(PdfPCell(Phrase("Descripción", cellFont)).apply {
            backgroundColor = cellBackgroundColor
        })
        table.addCell(PdfPCell(Phrase("Cantidad", cellFont)).apply {
            backgroundColor = cellBackgroundColor
        })
        table.addCell(PdfPCell(Phrase("Precio Unitario", cellFont)).apply {
            backgroundColor = cellBackgroundColor
        })
        table.addCell(PdfPCell(Phrase("Precio Total", cellFont)).apply {
            backgroundColor = cellBackgroundColor
        })

        var subtotal = 0.0
        items.forEach { item ->
            table.addCell(item.id.toString())
            table.addCell(item.nombre)
            table.addCell(item.quantity.toString())
            table.addCell(String.format("%.2f", item.precio))
            val itemTotal = item.precio * item.quantity
            table.addCell(String.format("%.2f", itemTotal))
            subtotal += itemTotal
        }
        document.add(table)

        val iva = subtotal * 0.12 // Asumiendo un IVA del 12%
        val total = subtotal + iva
        document.add(Paragraph("Subtotal: ${String.format("%.2f", subtotal)}", font))
        document.add(Paragraph("IVA 12%: ${String.format("%.2f", iva)}", font))
        document.add(Paragraph("Total: ${String.format("%.2f", total)}", font))

        document.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun moveComprobanteFileToDownloads(context: Context, file: File) {
    if (!file.exists()) {
        Toast.makeText(context, "Archivo no existe", Toast.LENGTH_SHORT).show()
        return
    }

    val downloadsDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    // Generamos un nombre de archivo único utilizando la hora actual
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val newFileName = "comprobante_$timestamp.pdf"
    val newFile = File(downloadsDir, newFileName)

    try {
        file.inputStream().use { input ->
            newFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        // No borramos el archivo original después de copiarlo
        Toast.makeText(
            context,
            "Comprobante guardado en Descargas: $newFileName",
            Toast.LENGTH_SHORT
        ).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error al descargar el archivo", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun ComprobanteHeader(
    onBackClick: () -> Unit,
    context: Context,
    file: File,
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
                text = "Comprobante",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { moveFileToDownloads(context, file) }
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp, 4.dp)
        ) {
            Text(
                text = "Descargar",
                color = MaterialTheme.colorScheme.error
            )
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Descargar",
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.padding(0.dp)
            )
        }


    }
}
