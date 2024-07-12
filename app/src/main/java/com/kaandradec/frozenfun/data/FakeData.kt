package com.kaandradec.frozenfun.data

import androidx.compose.runtime.mutableStateListOf
import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.model.CartItem

val listaFake = listOf(
    CartItem(
        1,
        "Cono simple",
        1.0,
        "cono_simple",
        0,
        descripcion = "Cono simple con 1 sabor de helado",
        sabores = mutableListOf(""),
        saboresSeleccionados = mutableListOf(),
        image = R.drawable.images,
        grageasSeleccionadas = mutableListOf(),
        extrasSeleccionados = mutableListOf(),
        tipo = "Cono"
    ),
    CartItem(
        2,
        "Cono doble",
        3.0,
        "cono_doble",
        0,
        descripcion = "  Cono doble de helado de vainilla y chocolate",
        sabores = mutableListOf(""),
        saboresSeleccionados = mutableListOf(),
        image = R.drawable.doble,
        grageasSeleccionadas = mutableListOf(),
        extrasSeleccionados = mutableListOf(),
        tipo = "Cono"
    ),
    CartItem(
        3,
        "Tulipan 2 sabores",
        3.0,
        "tulipan",
        0,
        descripcion = "     Tulipan de helado de vainilla y chocolate",
        sabores = mutableListOf(""),
        saboresSeleccionados = mutableListOf(),
        image = R.drawable.tuli_sabores,
        grageasSeleccionadas = mutableListOf(),
        extrasSeleccionados = mutableListOf(),
        tipo = "Tulipan"
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


