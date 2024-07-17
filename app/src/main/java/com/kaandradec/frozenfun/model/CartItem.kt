package com.kaandradec.frozenfun.model

data class CartItem(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: String,
    var quantity: Int,
    val sabores: List<String> = listOf(
        "Vainilla", "Maracuya", "Yogurt mora", "Chicle", "Galleta",
        "Manjar", "Kinder", "Café", "Guanabana", "Fresa", "Naranjilla",
        "Yogurt durazno", "Chocolate", "Tamarindo", "Mora", "Ron pasas"
    ),
    val descripcion: String,
    val image: Int,
    var saboresSeleccionados: MutableList<String> = mutableListOf(), // Actualiza con los sabores seleccionados
    var grageasSeleccionadas: MutableList<String> = mutableListOf(),
    var extrasSeleccionados: MutableList<String> = mutableListOf(),
    val tipo: String,
    val esNuevo: Boolean = false,
    val esPersonalizable: Boolean = false
)

data class TagItem(
    val id: Int,
    val tag: String,
    val imageResId: Int
)