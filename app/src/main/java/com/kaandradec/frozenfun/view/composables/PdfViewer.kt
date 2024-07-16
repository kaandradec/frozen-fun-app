package com.kaandradec.frozenfun.view.composables

import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import android.widget.ScrollView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.shockwave.pdfium.PdfiumCore
import java.io.File

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