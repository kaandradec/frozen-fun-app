package com.kaandradec.frozenfun.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kaandradec.frozenfun.model.CartItem

class CartViewModel : ViewModel() {
    val cartItems = mutableStateListOf<CartItem>()

    fun addItem(item: CartItem) {
        cartItems.add(item)
    }

    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getAllItems(): List<CartItem> {
        return cartItems
    }
}

var listaFalsa = listOf(
    CartItem("1", "Chocolate", 5.0, 2),
    CartItem("2", "Vainilla", 6.0, 1),
    CartItem("3", "Fresa", 6.0, 1),
    CartItem("4", "Limón", 6.0, 1),
    CartItem("5", "Menta", 6.0, 1),
    CartItem("6", "Nata", 5.0, 2),
    CartItem("7", "Oreo", 8.0, 1),
    CartItem("8", "Stracciatella", 8.0, 1),
)