package com.kaandradec.frozenfun.model

data class CartItem(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: String,
    var quantity: Int,
    val sabores: List<String>,
    val descripcion: String,
    val image: Int
)