package com.ksheerasagara.app.ui.screens.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.data.local.entity.ExpenseEntry
import com.ksheerasagara.app.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    val allExpenses: StateFlow<List<ExpenseEntry>> = expenseRepository.getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertExpense(
        date: Long,
        category: String,
        amount: Double,
        description: String,
        cowId: Long?
    ) {
        viewModelScope.launch {
            val entry = ExpenseEntry(
                date = date,
                category = category,
                amount = amount,
                description = description,
                cowId = cowId
            )
            expenseRepository.insertExpense(entry)
        }
    }

    fun deleteExpense(entry: ExpenseEntry) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(entry)
        }
    }
}
