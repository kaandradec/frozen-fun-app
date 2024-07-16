package com.kaandradec.frozenfun.util

import android.annotation.SuppressLint
import kotlin.random.Random

@SuppressLint("DefaultLocale")
fun generateInvoiceNumber(): String {
    val part1 = Random.nextInt(100, 999)
    val part2 = Random.nextInt(100, 999)
    val part3 = Random.nextInt(100000, 999999)
    return String.format("%03d-%03d-%06d", part1, part2, part3)
}