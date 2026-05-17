package com.ksheerasagara.app.ui.screens.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.ai.SuggestionEngine
import com.ksheerasagara.app.data.repository.ExpenseRepository
import com.ksheerasagara.app.data.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AiSuggestionViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val suggestionEngine: SuggestionEngine
) : ViewModel() {

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    init {
        generateSuggestions()
    }

    private fun generateSuggestions() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.timeInMillis
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = calendar.timeInMillis

        viewModelScope.launch {
            val expensesFlow = expenseRepository.getExpensesByCategory(startDate, endDate)
            val incomeFlow = incomeRepository.getTotalIncomeBetweenDates(startDate, endDate)

            combine(expensesFlow, incomeFlow) { expenses, income ->
                suggestionEngine.generateSuggestions(expenses, income ?: 0.0)
            }.collectLatest { newSuggestions ->
                _suggestions.value = newSuggestions
            }
        }
    }
}
