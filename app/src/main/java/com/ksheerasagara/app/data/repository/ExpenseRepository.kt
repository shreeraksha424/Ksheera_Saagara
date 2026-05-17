package com.ksheerasagara.app.data.repository

import com.ksheerasagara.app.data.local.dao.ExpenseCategoryTotal
import com.ksheerasagara.app.data.local.dao.ExpenseDao
import com.ksheerasagara.app.data.local.entity.ExpenseEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExpenseRepository {
    suspend fun insertExpense(expenseEntry: ExpenseEntry)
    suspend fun updateExpense(expenseEntry: ExpenseEntry)
    suspend fun deleteExpense(expenseEntry: ExpenseEntry)
    fun getAllExpenses(): Flow<List<ExpenseEntry>>
    fun getTotalExpenseBetweenDates(startDate: Long, endDate: Long): Flow<Double?>
    fun getTotalExpenseForCow(cowId: Long): Flow<Double?>
    fun getExpensesByCategory(startDate: Long, endDate: Long): Flow<List<ExpenseCategoryTotal>>
}

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override suspend fun insertExpense(expenseEntry: ExpenseEntry) {
        expenseDao.insert(expenseEntry)
    }

    override suspend fun updateExpense(expenseEntry: ExpenseEntry) {
        expenseDao.update(expenseEntry)
    }

    override suspend fun deleteExpense(expenseEntry: ExpenseEntry) {
        expenseDao.delete(expenseEntry)
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntry>> {
        return expenseDao.getAllExpenses()
    }

    override fun getTotalExpenseBetweenDates(startDate: Long, endDate: Long): Flow<Double?> {
        return expenseDao.getTotalExpenseBetweenDates(startDate, endDate)
    }

    override fun getTotalExpenseForCow(cowId: Long): Flow<Double?> {
        return expenseDao.getTotalExpenseForCow(cowId)
    }

    override fun getExpensesByCategory(
        startDate: Long,
        endDate: Long
    ): Flow<List<ExpenseCategoryTotal>> {
        return expenseDao.getExpensesByCategory(startDate, endDate)
    }
}
