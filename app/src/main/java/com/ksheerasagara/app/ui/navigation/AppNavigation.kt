package com.ksheerasagara.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ksheerasagara.app.ui.screens.ai.AiSuggestionScreen
import com.ksheerasagara.app.ui.screens.analytics.AnalyticsScreen
import com.ksheerasagara.app.ui.screens.cow.CowScreen
import com.ksheerasagara.app.ui.screens.dashboard.DashboardScreen
import com.ksheerasagara.app.ui.screens.expense.ExpenseScreen
import com.ksheerasagara.app.ui.screens.income.IncomeScreen

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Income : Screen("income")
    object Expense : Screen("expense")
    object Analytics : Screen("analytics")
    object Cow : Screen("cow")
    object AiSuggestions : Screen("ai_suggestions")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToIncome = { navController.navigate(Screen.Income.route) },
                onNavigateToExpense = { navController.navigate(Screen.Expense.route) }
            )
        }
        composable(Screen.Income.route) {
            IncomeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Expense.route) {
            ExpenseScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Analytics.route) {
            AnalyticsScreen()
        }
        composable(Screen.Cow.route) {
            CowScreen()
        }
        composable(Screen.AiSuggestions.route) {
            AiSuggestionScreen()
        }
    }
}
