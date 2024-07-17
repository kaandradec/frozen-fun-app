package com.kaandradec.frozenfun.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.util.isValidEcuadorMobilePhoneNumber
import com.kaandradec.frozenfun.util.isValidEcuadorianCedula
import com.kaandradec.frozenfun.util.isValidEmail
import com.kaandradec.frozenfun.util.onlyContainsLettersAndSingleSpace

@Composable
fun DatosScreen(
    navController: NavHostController
) {
    var nombreText by remember { mutableStateOf("") }
    var apellidoText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var telefonoNumero by remember { mutableStateOf("") }
    var cedulaNumero by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf<Int?>(null) } // 0 Efectivo, 1 Tarjeta
    var errorMessage by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }


    val context = LocalContext.current
    val termsAndConditionsText = remember {
        """
        TÉRMINOS Y CONDICIONES: COMPRAS EN LOCAL

Condiciones Generales
- Las compras realizadas a través de esta app son exclusivamente para consumo en el local.
- No se realizan entregas a domicilio a través de esta plataforma. Las compras deben ser recogidas en nuestro local físico.
- Los precios y disponibilidad de productos pueden variar sin previo aviso.

Horario de Servicio
- Nuestro horario de atención es de 08:00 AM - 20:00 PM.
- Cualquier pedido realizado fuera de nuestro horario de servicio será procesado al siguiente día hábil.

Condiciones de Pago
- Aceptamos pagos en efectivo y con tarjeta de crédito/débito.
- No aceptamos pagos con cheques o en moneda extranjera.

Política de Cancelación y Devolución
- No se aceptan cancelaciones de pedidos una vez realizada la compra.
- Las devoluciones se aceptan únicamente en caso de productos defectuosos o errores en la preparación del pedido. Se requiere la presentación del recibo de compra para realizar la devolución.

Aceptación de Términos
- Al realizar una compra a través de esta app, el cliente acepta automáticamente todos los términos y condiciones establecidos.
        """
    }
    val privacyPolicyText = """
    Política de Privacidad
    
    En FrozenFun, valoramos tu privacidad y nos comprometemos a proteger tus datos personales. 
    Utilizaremos tu información para gestionar nuestra relación comercial y mejorar nuestros servicios. 
    No compartiremos tus datos con terceros sin tu consentimiento explícito, a menos que sea necesario para cumplir con la ley o proteger nuestros derechos.
    
    Usaremos tus datos para procesar pedidos y mejorar tu experiencia con nosotros. Al utilizar nuestra aplicación, 
    aceptas nuestras prácticas de recopilación y uso de información según se describe en esta política.
"""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DatosHeader(
            onBackClick = { navController.popBackStack() }
        )
        Column(modifier = Modifier.weight(1f)) {
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }
            Text(
                text = "Método de pago",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 8.dp
                )
            )

            val options = listOf("Efectivo (Consumidor Final)", "Tarjeta")
            Column {
                options.forEachIndexed { i, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable { metodoPago = i }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(selected = metodoPago == i, onClick = null)
                        Text(text = option)
                    }
                }
            }

            val focusManager = LocalFocusManager.current

            if (metodoPago == 1) { // Datos de tarjeta
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .widthIn(max = 480.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                contentPadding = PaddingValues(vertical = 24.dp)
                ) {
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            label = { Text("Nombre") },
                            value = nombreText,
                            onValueChange = {
                                if (it.onlyContainsLettersAndSingleSpace()) {
                                    nombreText = it
                                } else {
                                    // Toast
                                    Toast.makeText(
                                        context,
                                        "Solo se permiten letras",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            singleLine = true,
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = nombreText.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { nombreText = "" }) {
                                        Icon(Icons.Outlined.Cancel, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            })

                    }
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            label = { Text("Apellido") },
                            value = apellidoText,
                            onValueChange = {
                                if (it.onlyContainsLettersAndSingleSpace()) {
                                    apellidoText = it
                                } else {
                                    // Toast
                                    Toast.makeText(
                                        context,
                                        "Solo se permiten letras",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            singleLine = true,
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = apellidoText.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { apellidoText = "" }) {
                                        Icon(Icons.Outlined.Cancel, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            })
                    }
                    item { Spacer(Modifier.height(4.dp)) }
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            label = { Text("Teléfono") },
                            value = telefonoNumero,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    telefonoNumero = it
                                } else {
                                    // Toast
                                    Toast.makeText(
                                        context,
                                        "Número de teléfono inválido, debe empezar con 09 y tener 10 dígitos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = telefonoNumero.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { telefonoNumero = "" }) {
                                        Icon(Icons.Outlined.Cancel, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            singleLine = true,
                        )
                    }
                    item { Spacer(Modifier.height(4.dp)) }
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            label = { Text("Email") },
                            value = emailText,
                            onValueChange = {
                                emailText = it
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = emailText.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { emailText = "" }) {
                                        Icon(Icons.Outlined.Cancel, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.clearFocus()
                            },
                            singleLine = true,
                        )
                    }
                    item { Spacer(Modifier.height(4.dp)) }
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = cedulaNumero,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    cedulaNumero = it
                                } else {
                                    // Toast
                                    Toast.makeText(
                                        context,
                                        "Cédula inválida",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            label = { Text("Cédula") },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = cedulaNumero.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { cedulaNumero = "" }) {
                                        Icon(Icons.Outlined.Cancel, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                        )
                    }
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Política de Privacidad",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = privacyPolicyText,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { termsAccepted = !termsAccepted }
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = { termsAccepted = it }
                )
                Text(text = "Acepto los términos y condiciones")
            }
            AnimatedVisibility(
                visible = termsAccepted,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Términos y Condiciones",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = termsAndConditionsText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                if (metodoPago == 0) { // Consumidor final
                    if (termsAccepted) {
                        navController.navigate(Screen.Comprobante)
                        return@Button
                    } else {
                        Toast.makeText(
                            context,
                            "Debe aceptar los términos y condiciones para continuar",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                }

                val error = validarDatos(
                    nombreText,
                    apellidoText,
                    emailText,
                    telefonoNumero,
                    cedulaNumero,
                    metodoPago,
                    termsAccepted
                )
                errorMessage = error

                if (!error.isEmpty()) {
                    Toast.makeText(
                        context,
                        error,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                if (!termsAccepted) {
                    Toast.makeText(
                        context,
                        "Debe aceptar los términos y condiciones para continuar",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                // Tarjeta
                navController.navigate(
                    Screen.AddPayment(
                        nombreText,
                        apellidoText,
                        emailText,
                        telefonoNumero,
                        cedulaNumero
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp, 16.dp)
        ) {
            Text("Guardar", fontSize = 18.sp)
        }
    }
}

@Composable
fun DatosHeader(
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
                text = "Ingrese sus datos de facturación",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }

    }
}


fun validarDatos(
    nombre: String,
    apellido: String,
    email: String,
    telefono: String,
    cedula: String,
    metodoPago: Int?,
    termsAccepted: Boolean
): String {
    var errorMessage = ""
    if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || metodoPago == null || !termsAccepted) {
        errorMessage = "Por favor, llene todos los campos y acepte los términos y condiciones"
        return errorMessage
    }
    if (!telefono.isValidEcuadorMobilePhoneNumber())
        return "Número de teléfono inválido, debe empezar con 09 y tener 10 dígitos"
    if (!email.isValidEmail())
        return "Email inválido, debe tener un formato válido como ejemplo@gmail.com"
    if (!cedula.isValidEcuadorianCedula())
        return "Cédula inválida"
    return errorMessage
}

