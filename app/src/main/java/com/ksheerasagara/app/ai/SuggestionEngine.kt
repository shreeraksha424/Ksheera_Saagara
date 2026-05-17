package com.ksheerasagara.app.ai

import com.ksheerasagara.app.data.local.dao.ExpenseCategoryTotal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionEngine @Inject constructor() {
    
    fun generateSuggestions(
        expenses: List<ExpenseCategoryTotal>,
        totalIncome: Double
    ): List<String> {
        val suggestions = mutableListOf<String>()
        val totalExpense = expenses.sumOf { it.total }
        
        if (totalIncome > 0 && totalExpense > totalIncome) {
            suggestions.add("⚠️ You are currently operating at a loss. Your expenses exceed your income.")
        }
        
        val fodderExpense = expenses.find { it.category == "Fodder" }?.total ?: 0.0
        if (totalExpense > 0 && (fodderExpense / totalExpense) > 0.6) {
            suggestions.add("💡 Feed cost is very high (>60% of expenses). Consider using home-grown fodder to reduce costs.")
        }
        
        val medicalExpense = expenses.find { it.category == "Medical" || it.category == "Vet" }?.total ?: 0.0
        if (totalExpense > 0 && (medicalExpense / totalExpense) > 0.2) {
            suggestions.add("💉 Frequent vet/medical expenses detected. Check preventive vaccination schedules and hygiene.")
        }

        if (totalIncome > 0 && totalExpense > 0 && (totalIncome - totalExpense) > 0) {
            suggestions.add("✅ Great job! You are running a profitable dairy this month.")
        }
        
        return suggestions
    }
}
