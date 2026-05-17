package com.ksheerasagara.app.ui.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.data.local.dao.ExpenseCategoryTotal
import com.ksheerasagara.app.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val _startDate: Long
    private val _endDate: Long

    init {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        _startDate = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        _endDate = calendar.timeInMillis
    }

    val expensesByCategory: StateFlow<List<ExpenseCategoryTotal>> = 
        expenseRepository.getExpensesByCategory(_startDate, _endDate)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
