package com.kaandradec.frozenfun.model

data class CartItem(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: String,
    var quantity: Int
)