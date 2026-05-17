package com.ksheerasagara.app.ui.screens.report

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.data.repository.AnalyticsRepository
import com.ksheerasagara.app.data.repository.ExpenseRepository
import com.ksheerasagara.app.data.repository.IncomeRepository
import com.ksheerasagara.app.utils.PdfGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val analyticsRepository: AnalyticsRepository
) : ViewModel() {

    fun generateMonthlyReport(context: Context) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            val startDate = calendar.timeInMillis

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            val endDate = calendar.timeInMillis

            val totalIncome = incomeRepository.getTotalIncomeBetweenDates(startDate, endDate).first() ?: 0.0
            val totalExpense = expenseRepository.getTotalExpenseBetweenDates(startDate, endDate).first() ?: 0.0
            val netProfit = analyticsRepository.getNetProfitBetweenDates(startDate, endDate).first()
            val expenseCategories = expenseRepository.getExpensesByCategory(startDate, endDate).first()

            PdfGenerator.generateAndSharePdf(
                context = context,
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                netProfit = netProfit,
                expenseCategories = expenseCategories
            )
        }
    }
}
