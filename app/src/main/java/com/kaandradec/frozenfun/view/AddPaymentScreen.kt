package com.kaandradec.frozenfun.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.util.onlyContainsLetters
import com.kaandradec.frozenfun.util.onlyContainsNonNegativeNumbers
import com.kaandradec.frozenfun.util.onlyContainsUpToFourNonNegativeNumbers
import com.kaandradec.frozenfun.util.onlyContainsUpToThreeNonNegativeNumbers
import com.kaandradec.frozenfun.util.onlyContainsZeroToSixteenNonNegativeNumbers
import com.kaandradec.frozenfun.view.composables.CreditCardFilter
import com.kaandradec.frozenfun.view.composables.InputItem
import com.kaandradec.frozenfun.view.composables.PaymentCard
import com.kaandradec.frozenfun.viewmodel.CartViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    navController: NavHostController,
    nombre: String,
    apellido: String,
    telefono: String,
    email: String,
    cedula: String,
) {
    var nameText by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var expiryNumber by remember { mutableStateOf(TextFieldValue()) }
    var cvcNumber by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf("") }

    Column(
    )
    {
        TajetaHeader(onBackClick = { navController.popBackStack() })
        PaymentCard(
            nameText,
            cardNumber,
            expiryNumber,
            cvcNumber
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                InputItem(
                    textFieldValue = nameText,
//                        label = stringResource(id = R.string.card_holder_name),
                    label = "Nombre del propietario de la tarjeta",
                    onTextChanged = {
                        if (it.text.onlyContainsLetters()) {
                            nameText = it
                        } else {
                            // Toast
                            Toast.makeText(
                                context,
                                "Solo se permiten letras",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            item {
                InputItem(
                    textFieldValue = cardNumber,
//                        label = stringResource(id = R.string.card_holder_number),
                    label = "Número de la tarjeta",
                    keyboardType = KeyboardType.Number,
                    onTextChanged = {
                        if (it.text.onlyContainsZeroToSixteenNonNegativeNumbers()) {
                            cardNumber = it
                        } else {
                            // Aquí puedes manejar el caso cuando el texto ingresado contiene caracteres no permitidos
                            // Por ejemplo, puedes mostrar un mensaje de error
                            Toast.makeText(
                                context,
                                "Solo se permiten 16 números no negativos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    visualTransformation = CreditCardFilter
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InputItem(
                        textFieldValue = expiryNumber,
                        label = "Fecha de vencimiento",
                        keyboardType = KeyboardType.Number,
                        onTextChanged = {
                            if (it.text.onlyContainsUpToFourNonNegativeNumbers()) {
                                expiryNumber = it
                            } else {
                                // Aquí puedes manejar el caso cuando el texto ingresado contiene caracteres no permitidos
                                // Por ejemplo, puedes mostrar un mensaje de error
                                Toast.makeText(
                                    context,
                                    "Solo se permiten números no negativos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    InputItem(
                        textFieldValue = cvcNumber,
                        label = "CVC",
                        keyboardType = KeyboardType.Number,
                        onTextChanged = {
                            if (it.text.onlyContainsUpToThreeNonNegativeNumbers()) {
                                cvcNumber = it
                            } else {
                                // Aquí puedes manejar el caso cuando el texto ingresado contiene caracteres no permitidos
                                // Por ejemplo, puedes mostrar un mensaje de error
                                Toast.makeText(
                                    context,
                                    "Solo se permiten 3 números no negativos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        errorMessage =
                            validateCardData(nameText, cardNumber, expiryNumber, cvcNumber)
                        if (errorMessage.isEmpty()) {
                            navController.navigate(
                                Screen.Factura(
                                    nombre = nombre,
                                    apellido = apellido,
                                    telefono = telefono,
                                    email = email,
                                    cedula = cedula
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Realizar pago",
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}


fun validateCardData(
    nameText: TextFieldValue,
    cardNumber: TextFieldValue,
    expiryNumber: TextFieldValue,
    cvcNumber: TextFieldValue
): String {
    if (nameText.text.isEmpty()) {
        return "El nombre no puede estar vacío"
    } else if (cardNumber.text.length != 16 || !cardNumber.text.onlyContainsNonNegativeNumbers()) {
        return "El número de la tarjeta debe tener 16 dígitos"
    } else if (expiryNumber.text.length != 4 || !expiryNumber.text.onlyContainsNonNegativeNumbers()) {
        return "La fecha de vencimiento debe tener 4 dígitos"
    } else if (cvcNumber.text.length != 3 || !cvcNumber.text.onlyContainsNonNegativeNumbers()) {
        return "El CVC debe tener 3 dígitos"
    } else {
        return ""
    }
}


@Composable
fun TajetaHeader(
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Ingrese los datos de la tarjeta",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
    }
}