package com.kaandradec.frozenfun.view.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputItem(
    textFieldValue: TextFieldValue,
    label: String,
    onTextChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val padding = rememberScaledPadding() // Recuerda el padding escalado

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { onTextChanged(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        textStyle = textStyle,
        maxLines = 1,
        singleLine = true,
        label = {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                    alpha = ContentAlpha.medium
                )
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(rememberScaledPadding()) // Aplica el padding escalado
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
//            textColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.disabled),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.disabled),
//            backgroundColor = MaterialTheme.colorScheme.surface,
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.disabled)
        ),
        modifier = modifier,
        visualTransformation = visualTransformation
    )
}

@Composable
fun rememberScaledPadding(): PaddingValues {
    // Ajusta el valor de padding en función del tamaño de texto del sistema
    val scaledPadding = with(LocalDensity.current) { 16.sp.toDp() }
    return PaddingValues(scaledPadding)
}
