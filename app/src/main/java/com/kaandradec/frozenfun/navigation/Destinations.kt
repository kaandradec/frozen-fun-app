package com.kaandradec.frozenfun.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Seleccionar : Screen()

    @Serializable
    data object Personalizar : Screen()

    @Serializable
    data object Ajustes : Screen()

    @Serializable
    data object Carrito : Screen()

    @Serializable
    data object Test : Screen()

    @Serializable
    data class Detalle(val id: Int) : Screen()

    @Serializable
    data class Factura(val cedula: String, val correo: String) : Screen()

    @Serializable
    data object AddPayment : Screen()

    @Serializable
    data object Datos : Screen()

}