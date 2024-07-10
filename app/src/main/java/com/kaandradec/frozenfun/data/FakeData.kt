package com.kaandradec.frozenfun.data

import androidx.compose.runtime.mutableStateListOf
import com.kaandradec.frozenfun.model.CartItem

val listaFake = listOf(
    CartItem(
        1,
        "Cono simple",
        1.0,
        "cono_simple",
        0,
        descripcion = "Cono simple de helado de vainilla",
        sabores = listOf("Vainilla"),
        image = com.kaandradec.frozenfun.R.drawable.images
    ),
    CartItem(
        2,
        "Cono doble",
        3.0,
        "cono_doble",
        0,
        descripcion = "Cono doble de helado de vainilla y chocolate",
        sabores = listOf("Vainilla", "Chocolate"),
        image = com.kaandradec.frozenfun.R.drawable.doble
    ),
    CartItem(
        3,
        "Tulipan 2 sabores",
        3.0,
        "tulipan",
        0,
        descripcion = "Tulipan de helado de vainilla y chocolate",
        sabores = listOf("Vainilla", "Chocolate"),
        image = com.kaandradec.frozenfun.R.drawable.tuli_sabores
    ),
    CartItem(
        4,
        "Banana Split",
        1.0,
        "banana_split",
        0,
        sabores = listOf("Vainilla", "Chocolate"),
        descripcion = "Banana Split de helado de vainilla y chocolate",
        image = com.kaandradec.frozenfun.R.drawable.banana
    ),
    CartItem(
        5,
        "Helado copa doble",
        2.5,
        "copa_doble",
        0,
        sabores = listOf("Vainilla", "Chocolate"),
        descripcion = "Copa doble de helado de vainilla y chocolate",
        image = com.kaandradec.frozenfun.R.drawable.copa_doble
    ),
    CartItem(
        3,
        "Tulipan extra",
        3.0,
        "tulipan_queso",
        0,
        sabores = listOf("Vainilla", "Chocolate", "Queso", "Crema"),
        descripcion = "Tulipan de helado de vainilla y chocolate con queso y crema",
        image = com.kaandradec.frozenfun.R.drawable.tuli_sabores_queso
    )
)

class Database {
    private val items = mutableStateListOf<CartItem>()

    init {
        items.addAll(listaFake)
    }

    fun getAllItems(): List<CartItem> {
        return items.toList()
    }

    fun getItemById(id: Int): CartItem? {
        return items.find { it.id == id }
    }

    fun addItem(item: CartItem) {
        items.add(item)
    }

    fun updateItem(item: CartItem) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items[index] = item
        }
    }

    fun deleteItem(item: CartItem) {
        items.remove(item)
    }
}


