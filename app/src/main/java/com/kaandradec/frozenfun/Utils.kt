package com.kaandradec.frozenfun

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream

// Obtenemos la altura de la barra de estado
@Composable
@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun getStatusBarHeightDp(): Dp {
    val context = LocalContext.current
    val statusBarHeight = context.resources.getDimensionPixelSize(
        context.resources.getIdentifier("status_bar_height", "dimen", "android")
    )
    return with(LocalDensity.current) { statusBarHeight.toDp() }
}

// PDF
fun createPdf() {
    try {
        // 1. Crear el documento
        val document = Document()

        // 2. Crear el PdfWriter
        val writer = PdfWriter.getInstance(document, FileOutputStream("path_to_your_file.pdf"))

        // 3. Abrir el documento
        document.open()

        // 4. Agregar contenido al documento
        document.add(Paragraph("Hello, world!"))

        // 5. Cerrar el documento
        document.close()

        // 6. Cerrar el writer
        writer.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}