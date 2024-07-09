package com.kaandradec.frozenfun.viewmodel

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kaandradec.frozenfun.MainActivity
import com.kaandradec.frozenfun.model.CartItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CartViewModel : ViewModel() {

    fun addItem(item: CartItem) {
        _cartItems.add(item)
    }

    fun removeItem(item: CartItem) {
        _cartItems.remove(item)
    }

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun clearCart() {
        _cartItems.clear()
    }

    fun addHelado(helado: CartItem) {
        val existingHelado = _cartItems.find { it.id == helado.id }
        if (existingHelado != null) {
            val index = _cartItems.indexOf(existingHelado)
            _cartItems[index] = existingHelado.copy(quantity = existingHelado.quantity + 1)
        } else {
            _cartItems.add(helado.copy(quantity = 1))
        }
    }

    fun removeHelado(helado: CartItem) {
        val existingHelado = _cartItems.find { it.id == helado.id }
        if (existingHelado != null) {
            val index = _cartItems.indexOf(existingHelado)
            if (existingHelado.quantity > 1) {
                _cartItems[index] = existingHelado.copy(quantity = existingHelado.quantity - 1)
            } else {
                _cartItems.remove(existingHelado)
            }
        }
    }
}