package com.kaandradec.frozenfun

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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