package com.kaandradec.frozenfun.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Seleccionar : Screen()

    @Serializable
    data object Personalizar: Screen()

    @Serializable
    data object Ajustes : Screen()

    @Serializable
    data object Carrito : Screen()

}