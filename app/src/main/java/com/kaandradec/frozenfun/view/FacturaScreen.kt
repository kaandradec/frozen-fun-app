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
fun FacturaScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    cedula: String,
    correo: String
) {
    val context = LocalContext.current

    val lista = cartViewModel.cartItems

    createInvoicePdf(context, lista, correo, cedula)

    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "factura.pdf")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
        Spacer(modifier = Modifier.height(getStatusBarHeightDp()))
        FacturaHeader(
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
        Spacer(modifier = Modifier.height(16.dp))

        PdfViewer(pdfFile = file)
        Button(
            onClick = { moveFileToDownloads(context, file) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Descargar archivo")
        }
    }
}

@Composable
fun PdfViewer(pdfFile: File) {
    val pdfiumCore = PdfiumCore(LocalContext.current)
    val parcelFileDescriptor =
        ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
    val pdfDocument = pdfiumCore.newDocument(parcelFileDescriptor)

    val pageIndex = 0
    pdfiumCore.openPage(pdfDocument, pageIndex)

    val pageWidth = pdfiumCore.getPageWidthPoint(pdfDocument, pageIndex)
    val pageHeight = pdfiumCore.getPageHeightPoint(pdfDocument, pageIndex)

    val bitmap = Bitmap.createBitmap(pageWidth, pageHeight, Bitmap.Config.ARGB_8888)

    pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageIndex, 0, 0, pageWidth, pageHeight)

    AndroidView(
        factory = { context ->
            ScrollView(context).apply {
                addView(ImageView(context).apply {
                    setImageBitmap(bitmap)
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    adjustViewBounds = true
                })
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(pageHeight.dp), // Ajusta la altura para permitir el scroll
        update = { view ->
            (view as ScrollView).getChildAt(0)?.let { it as ImageView }?.setImageBitmap(bitmap)
        }
    )

    pdfiumCore.closeDocument(pdfDocument)
}

@SuppressLint("DefaultLocale")
fun createInvoicePdf(context: Context, items: List<CartItem>, correo: String, cedula: String) {
    try {
        val document = Document()
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "factura.pdf")
        PdfWriter.getInstance(document, FileOutputStream(file))
        document.open()

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = Image.getInstance(stream.toByteArray())
        image.scaleToFit(100f, 100f)
        document.add(image)

        val titleFont = Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)
        val font = Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        val invoiceNumber = generateInvoiceNumber()

        document.add(Paragraph("Frozen Fun", titleFont))
        document.add(Paragraph("PICHINCHA / QUITO / AV. CRISTOBAL COLON Y AV AMERICA", font))
        document.add(Paragraph("Fecha: $date", font))
        document.add(Paragraph("RUC: 1791415132002", font))
        document.add(Paragraph("Número de Factura: $invoiceNumber", font))
        document.add(Paragraph("Razón Social / Nombres y Apellidos: BRYAN", font))
        document.add(Paragraph("RUC / CI: $cedula", font))
        document.add(Paragraph("Fecha Emisión: 10/07/2024", font))
        document.add(Paragraph("Comprobante que se modifica: FACTURA", font))
        document.add(Paragraph("Razón de Modificación: Anulación", font))

        val table = PdfPTable(5)
        table.widthPercentage = 100f
        val cellFont = Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
        val cellBackgroundColor = BaseColor(200, 200, 200)

        val idCell = PdfPCell(Phrase("Código", cellFont))
        idCell.backgroundColor = cellBackgroundColor
        table.addCell(idCell)

        val nameCell = PdfPCell(Phrase("Descripción", cellFont))
        nameCell.backgroundColor = cellBackgroundColor
        table.addCell(nameCell)

        val quantityCell = PdfPCell(Phrase("Cantidad", cellFont))
        quantityCell.backgroundColor = cellBackgroundColor
        table.addCell(quantityCell)

        val priceCell = PdfPCell(Phrase("Precio Unitario", cellFont))
        priceCell.backgroundColor = cellBackgroundColor
        table.addCell(priceCell)

        val totalCell = PdfPCell(Phrase("Precio Total", cellFont))
        totalCell.backgroundColor = cellBackgroundColor
        table.addCell(totalCell)

        var subtotal = 0.0
        for (item in items) {
            table.addCell(item.id.toString())
            table.addCell(item.nombre)
            table.addCell(item.quantity.toString())
            table.addCell(String.format("%.2f", item.precio))
            val itemTotal = item.precio * item.quantity
            table.addCell(String.format("%.2f", itemTotal))
            subtotal += itemTotal
        }
        document.add(table)

        val iva = subtotal * 0.15
        val total = subtotal + iva
        document.add(Paragraph("Subtotal: ${String.format("%.2f", subtotal)}", font))
        document.add(Paragraph("IVA 15%: ${String.format("%.2f", iva)}", font))
        document.add(Paragraph("Total: ${String.format("%.2f", total)}", font))

        document.add(Paragraph("CORREO: $correo", font))
        document.add(Paragraph("ADMINISTRADOR: PEDRO CHAMBA", font))
        document.add(
            Paragraph(
                "Gran Contribuyente: GRAN CONTRIBUYENTE MEDIANTE RESOLUCIÓN NAC-GCFDIOC21-00000090-E",
                font
            )
        )

        document.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@SuppressLint("DefaultLocale")
fun generateInvoiceNumber(): String {
    val part1 = Random.nextInt(100, 999)
    val part2 = Random.nextInt(100, 999)
    val part3 = Random.nextInt(100000, 999999)
    return String.format("%03d-%03d-%06d", part1, part2, part3)
}

fun moveFileToDownloads(context: Context, file: File) {
    if (!file.exists()) {
        Toast.makeText(context, "Archivo no existe", Toast.LENGTH_SHORT).show()
        return
    }

    val downloadsDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    // Generamos un nombre de archivo único utilizando la hora actual
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val newFileName = "factura_$timestamp.pdf"
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
            "Factura guardada en Descargas: $newFileName",
            Toast.LENGTH_SHORT
        ).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error al descargar el archivo", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun FacturaHeader(
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
                text = "Factura",
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
                text = "Descargar factura",
                color = MaterialTheme.colorScheme.error
            )
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Descargar factura",
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.padding(0.dp)
            )
        }


    }
}
