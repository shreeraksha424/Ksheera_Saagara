package com.ksheerasagara.app.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.data.repository.AnalyticsRepository
import com.ksheerasagara.app.data.repository.ExpenseRepository
import com.ksheerasagara.app.data.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val analyticsRepository: AnalyticsRepository
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

    val totalIncome: StateFlow<Double> = incomeRepository.getTotalIncomeBetweenDates(_startDate, _endDate)
        .combine(kotlinx.coroutines.flow.flowOf(0.0)) { income, _ -> income ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalExpense: StateFlow<Double> = expenseRepository.getTotalExpenseBetweenDates(_startDate, _endDate)
        .combine(kotlinx.coroutines.flow.flowOf(0.0)) { expense, _ -> expense ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val netProfit: StateFlow<Double> = analyticsRepository.getNetProfitBetweenDates(_startDate, _endDate)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
}
