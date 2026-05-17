package com.ksheerasagara.app.data.repository

import com.ksheerasagara.app.data.local.dao.ExpenseDao
import com.ksheerasagara.app.data.local.dao.IncomeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface AnalyticsRepository {
    fun getNetProfitBetweenDates(startDate: Long, endDate: Long): Flow<Double>
}

class AnalyticsRepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao
) : AnalyticsRepository {
    override fun getNetProfitBetweenDates(startDate: Long, endDate: Long): Flow<Double> {
        val incomeFlow = incomeDao.getTotalIncomeBetweenDates(startDate, endDate)
        val expenseFlow = expenseDao.getTotalExpenseBetweenDates(startDate, endDate)

        return combine(incomeFlow, expenseFlow) { income, expense ->
            (income ?: 0.0) - (expense ?: 0.0)
        }
    }
}
