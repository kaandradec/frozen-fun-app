package com.kaandradec.frozenfun.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kaandradec.frozenfun.model.CartItem

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun addItem(item: CartItem) {
        _cartItems.add(item)
    }

    fun removeItem(item: CartItem) {
        _cartItems.remove(item)
    }


    fun clearCart() {
        _cartItems.clear()
    }

    fun findItemById(id: Int): CartItem? {
        println("ID es: $id")
        return _cartItems.find { it.id == id }
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