package com.kaandradec.frozenfun.data

import com.kaandradec.frozenfun.R
import com.kaandradec.frozenfun.model.CartItem
import com.kaandradec.frozenfun.model.TagItem

val datos =
    listOf(
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
            tipo = "Cono",
            esNuevo = false,
            esPersonalizable = true
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
            tipo = "Cono",
            esNuevo = false,
            esPersonalizable = true
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
            tipo = "Tulipan",
            esNuevo = false,
            esPersonalizable = true
        ),
        CartItem(
            4,
            "Banana Split",
            1.0,
            "banana_split",
            0,
            sabores = mutableListOf("Banana Slit con 3 sabores de helado"),
            saboresSeleccionados = mutableListOf(),
            descripcion = "",
            image = R.drawable.banana,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Banana Split",
            esNuevo = false,
            esPersonalizable = true,
        ),
        CartItem(
            5,
            "Helado copa doble",
            2.5,
            "copa_doble",
            0,
            sabores = mutableListOf("Copa de helado con dos sabores de helado"),
            saboresSeleccionados = mutableListOf(),
            descripcion = "",
            image = R.drawable.copa_doble,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Copa",
            esNuevo = false,
            esPersonalizable = true,
        ),
        CartItem(
            6,
            "Tulipan extra",
            3.0,
            "tulipan_queso",
            0,
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf(),
            descripcion = "Tulipan de helado de vainilla y chocolate con queso y crema",
            image = R.drawable.tuli_sabores_queso,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Tulipan",
            esNuevo = true,
            esPersonalizable = true,
        ),
        // Cono de otra marca
        CartItem(
            id = 7,
            nombre = "Pastel oreo",
            precio = 10.0,
            imagen = "Pastel oreo",
            quantity = 0,
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf("Vainilla y Chocolate"),
            descripcion = "Pastel completo de oreo",
            image = R.drawable.pastel_helado_oreo,
            grageasSeleccionadas = mutableListOf("N|A"),
            extrasSeleccionados = mutableListOf("Galletas Oreo y Crema"),
            tipo = "Otro",
            esNuevo = true,
            esPersonalizable = false,
        ),
        CartItem(
            id = 8,
            nombre = "Pastel de frutos rojos",
            precio = 11.0,
            imagen = "Pastel de frutos rojos",
            quantity = 0,
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf(),
            descripcion = "Pastel completo de frutos rojos",
            image = R.drawable.pastel_helado_frutosrojos,
            grageasSeleccionadas = mutableListOf("NA"),
            extrasSeleccionados = mutableListOf(),
            tipo = "Otro",
            esNuevo = true,
            esPersonalizable = false,
        ),
        CartItem(
            id = 9,
            nombre = "Come y bebe",
            precio = 1.0,
            imagen = "Come y bebe",
            quantity = 0,
            sabores = mutableListOf(""),
            saboresSeleccionados = mutableListOf(),
            descripcion = "Helado de naranja con frutas",
            image = R.drawable.helado_comeybebe,
            grageasSeleccionadas = mutableListOf(),
            extrasSeleccionados = mutableListOf(),
            tipo = "Otro",
            esNuevo = true,
            esPersonalizable = false,
        ),
    )


val tagsList = listOf(
    TagItem(id = 1, tag = "Nuevos", imageResId = R.drawable.helado_nuevos),
    TagItem(id = 2, tag = "Personalizables", imageResId = R.drawable.helado_personalizados),
    TagItem(id = 3, tag = "Otros ", imageResId = R.drawable.helado_marcas),
)

//class Database {
//    private val items = mutableStateListOf<CartItem>()
//
//    init {
//        items.addAll(listaFake)
//    }
//
//    fun getAllItems(): List<CartItem> {
//        return items.toList()
//    }
//
//    fun getItemById(id: Int): CartItem? {
//        return items.find { it.id == id }
//    }
//
//    fun addItem(item: CartItem) {
//        items.add(item)
//    }
//
//    fun updateItem(item: CartItem) {
//        val index = items.indexOfFirst { it.id == item.id }
//        if (index != -1) {
//            items[index] = item
//        }
//    }
//
//    fun deleteItem(item: CartItem) {
//        items.remove(item)
//    }
//}


