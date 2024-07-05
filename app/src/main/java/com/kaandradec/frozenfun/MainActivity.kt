package com.kaandradec.frozenfun

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.navigation.SetupGraph
import com.kaandradec.frozenfun.ui.theme.FrozenFunTheme
import com.kaandradec.frozenfun.viewmodel.CartViewModel
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tabItems = listOf(
            BottomNavigationItem(
                title = "Menu",
                selectedIcon = Icons.Filled.ShoppingBasket,
                unselectedIcon = Icons.Outlined.ShoppingBasket,
            ),
            BottomNavigationItem(
                title = "Personalizar",
                selectedIcon = Icons.Filled.Create,
                unselectedIcon = Icons.Outlined.Create
            ),
            BottomNavigationItem(
                title = "Ajustes",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            ),
        )

        setContent {
            FrozenFunTheme {
                val navController = rememberNavController()
                val cartViewModel: CartViewModel = viewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                            Text(text = "Pagar helados")

                    }

                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                tabItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(
                                                when (index) {
                                                    0 -> Screen.Seleccionar
                                                    1 -> Screen.Personalizar
                                                    2 -> Screen.Ajustes
                                                    else -> throw IllegalArgumentException("Unknown route")
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        SetupGraph(navController = navController, cartViewModel)
                    }
                }
            }
        }
    }

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
