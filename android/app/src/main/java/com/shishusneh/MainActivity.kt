package com.shishusneh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.shishusneh.ui.screens.*
import com.shishusneh.ui.theme.*
import com.shishusneh.viewmodel.MainViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ShishuSnehTheme { ShishuSnehApp() } }
    }
}

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    data object Home : Screen("home", Icons.Default.Home, "Home")
    data object Vaccines : Screen("vaccines", Icons.Default.Vaccines, "Vaccines")
    data object Growth : Screen("growth", Icons.Default.ShowChart, "Growth")
    data object Nutrition : Screen("nutrition", Icons.Default.Restaurant, "Nutrition")
    data object Register : Screen("register", Icons.Default.PersonAdd, "Register")
}

@Composable
fun ShishuSnehApp(vm: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val tabs = listOf(Screen.Home, Screen.Vaccines, Screen.Growth, Screen.Nutrition)

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Register.route) {
                NavigationBar(containerColor = Color.White.copy(alpha = 0.92f)) {
                    tabs.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, screen.label) },
                            label = { Text(screen.label, style = MaterialTheme.typography.labelSmall) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Coral,
                                selectedTextColor = Coral,
                                indicatorColor = CoralLight.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        },
        containerColor = BgPrimary
    ) { padding ->
        NavHost(navController, Screen.Home.route, Modifier.padding(padding)) {
            composable(Screen.Home.route) {
                HomeScreen(
                    baby = vm.currentBaby,
                    onRegister = { navController.navigate(Screen.Register.route) },
                    onVaccines = { navController.navigate(Screen.Vaccines.route) },
                    onGrowth = { navController.navigate(Screen.Growth.route) },
                    onNutrition = { navController.navigate(Screen.Nutrition.route) }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(isLoading = vm.isLoading) { name, dob, weight ->
                    vm.registerBaby(name, dob, weight) { navController.popBackStack() }
                }
            }
            composable(Screen.Vaccines.route) {
                VaccinesScreen(vaccines = vm.vaccines, isLoading = vm.isLoading, onLoad = { vm.loadVaccines() })
            }
            composable(Screen.Growth.route) {
                GrowthScreen(healthLogs = vm.healthLogs, isLoading = vm.isLoading, onLoad = { vm.loadHealthLogs() }) { d, w, h, m ->
                    vm.addHealthLog(d, w, h, m) {}
                }
            }
            composable(Screen.Nutrition.route) {
                val age = vm.currentBaby?.let {
                    ChronoUnit.MONTHS.between(LocalDate.parse(it.dateOfBirth), LocalDate.now()).toInt()
                } ?: 0
                NutritionScreen(guide = vm.nutritionGuide, isLoading = vm.isLoading, defaultAge = age) { a, ing ->
                    vm.generateNutrition(a, ing)
                }
            }
        }
    }

    // Error snackbar
    vm.error?.let { msg ->
        LaunchedEffect(msg) {
            // Auto-clear after showing
            kotlinx.coroutines.delay(3000)
            vm.clearError()
        }
    }
}
