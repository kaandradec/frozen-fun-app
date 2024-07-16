package com.kaandradec.frozenfun.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusDirection
import androidx.core.text.isDigitsOnly
import com.kaandradec.frozenfun.util.isValidEcuadorMobilePhoneNumber
import com.kaandradec.frozenfun.util.isValidEcuadorianCedula
import com.kaandradec.frozenfun.util.isValidEmail
import com.kaandradec.frozenfun.util.onlyContainsLetters

@Composable
fun DatosScreen() {
    var nombreText by remember { mutableStateOf("") }
    var apellidoText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var telefonoNumero by remember { mutableStateOf("") }
    var cedulaNumero by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf<Int?>(null) } // 0 Efectivo, 1 Tarjeta
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }
            val focusManager = LocalFocusManager.current
            LazyColumn(
                modifier = Modifier.widthIn(max = 480.dp),
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
                            if (it.onlyContainsLetters()) {
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
                            if (it.onlyContainsLetters()) {
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
                }
                item {
                    val options = listOf("Efectivo", "Tarjeta")
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
                }
            }
            Button(
                onClick = {
                    val error = validarDatos(
                        nombreText,
                        apellidoText,
                        emailText,
                        telefonoNumero,
                        cedulaNumero,
                        metodoPago
                    )
                    errorMessage = error

                    if (error.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Datos validados",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(64.dp, 16.dp)
            ) {
                Text("Guardar", fontSize = 18.sp)
            }
        }
    }
}


fun validarDatos(
    nombre: String,
    apellido: String,
    email: String,
    telefono: String,
    cedula: String,
    metodoPago: Int?
): String {
    val errorMessage = ""
    if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || metodoPago == null) {
        return "Por favor, llene todos los campos"
    }
    if (!telefono.isValidEcuadorMobilePhoneNumber())
        return "Número de teléfono inválido, debe empezar con 09 y tener 10 dígitos"
    if (!email.isValidEmail())
        return "Email inválido, debe tener un formato válido como ejemplo@gmail.com"
    if (!cedula.isValidEcuadorianCedula())
        return "Cédula inválida"
    return errorMessage
}