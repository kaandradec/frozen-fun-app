package com.kaandradec.frozenfun.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Seleccionar : Screen()

    @Serializable
    data object Ayuda : Screen()

    @Serializable
    data object Info : Screen()

    @Serializable
    data object Carrito : Screen()

    @Serializable
    data object Test : Screen()

    @Serializable
    data class Detalle(val id: Int) : Screen()

    @Serializable
    data class Factura(
        val nombre: String,
        val apellido: String,
        val telefono: String,
        val email: String,
        val cedula: String,
    ) : Screen()

    @Serializable
    data object Comprobante : Screen()

    @Serializable
    data class AddPayment(
        val nombre: String,
        val apellido: String,
        val telefono: String,
        val email: String,
        val cedula: String,
    ) : Screen()

    @Serializable
    data object Datos : Screen()

    @Serializable
    data object QR : Screen()

}