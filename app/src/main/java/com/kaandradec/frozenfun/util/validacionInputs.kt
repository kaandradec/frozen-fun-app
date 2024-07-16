package com.kaandradec.frozenfun.util

fun String.onlyContainsLetters(): Boolean {
    // Esta expresión regular coincide con cualquier cadena que solo contenga letras (mayúsculas o minúsculas)
    val regex = Regex("^[a-zA-Z]*$")
    return this.matches(regex)
}

fun String.onlyContainsZeroToSixteenNonNegativeNumbers(): Boolean {
    // Esta expresión regular coincide con cualquier cadena que contenga de 0 a 16 números
    val regex = Regex("^[0-9]{0,16}$")
    return this.matches(regex)
}

fun String.onlyContainsNonNegativeNumbers(): Boolean {
    // Esta expresión regular coincide con cualquier cadena que solo contenga números
    val regex = Regex("^[0-9]*$")
    return this.matches(regex)
}

fun String.onlyContainsUpToFourNonNegativeNumbers(): Boolean {
    // Esta expresión regular coincide con cualquier cadena que contenga de 0 a 4 números
    val regex = Regex("^[0-9]{0,4}$")
    return this.matches(regex)
}

fun String.onlyContainsUpToThreeNonNegativeNumbers(): Boolean {
    // Esta expresión regular coincide con cualquier cadena que contenga de 0 a 3 números
    val regex = Regex("^[0-9]{0,3}$")
    return this.matches(regex)
}

fun String.isValidEcuadorMobilePhoneNumber(): Boolean {
    // Esta expresión regular coincide con los números de teléfono móviles de Ecuador
    val regex = Regex("^09[0-9]{8}$")
    return this.matches(regex)
}

fun String.isValidEmail(): Boolean {
    // Esta expresión regular coincide con el formato general de un correo electrónico
    val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    return this.matches(regex)
}

fun String.isValidEcuadorianCedula(): Boolean {
    if (this.length != 10) {
        return false
    }
    val province = this.substring(0, 2).toInt()
    if (province < 1 || province > 24) {
        return false
    }
    val digits = this.map { it.toString().toInt() }
    val thirdDigit = digits[2]
    if (thirdDigit > 6) {
        return false
    }
    val coefficients = listOf(2, 1, 2, 1, 2, 1, 2, 1, 2)
    var sum = 0
    for (i in 0 until 9) {
        var partial = digits[i] * coefficients[i]
        if (partial > 9) partial -= 9
        sum += partial
    }
    val tenthDigit = if (sum % 10 == 0) 0 else 10 - sum % 10
    return tenthDigit == digits[9]
}