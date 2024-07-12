package com.kaandradec.frozenfun

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kaandradec.frozenfun.navigation.Screen
import com.kaandradec.frozenfun.navigation.SetupGraph
import com.kaandradec.frozenfun.ui.theme.FrozenFunTheme
import com.kaandradec.frozenfun.viewmodel.CartViewModel

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

//            var permisionStatus by remember {
//                mutableStateOf("Permiso no solicitado")
//            }
//            val permissionLauncher = rememberLauncherForActivityResult(
//                contract =
//                ActivityResultContracts.RequestPermission()
//            ) { granted ->
//                permisionStatus =
//                    if (granted) "Permiso concedido" else "Permiso denegado"
//            }

            FrozenFunTheme {
                val navController = rememberNavController()
                val cartViewModel: CartViewModel = viewModel()
//
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Bottom,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(text = permisionStatus)
//                        Button(onClick = {
//                            permissionLauncher.launch(
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                            )
//                        }) {
//                            Text(text = "Solicitar permisos")
//                        }
//                    }
//                }

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
                                                2 -> Screen.Test
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
