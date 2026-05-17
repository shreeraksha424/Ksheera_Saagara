package com.ksheerasagara.app.data.repository

import com.ksheerasagara.app.data.local.dao.IncomeDao
import com.ksheerasagara.app.data.local.entity.IncomeEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IncomeRepository {
    suspend fun insertIncome(incomeEntry: IncomeEntry)
    suspend fun updateIncome(incomeEntry: IncomeEntry)
    suspend fun deleteIncome(incomeEntry: IncomeEntry)
    fun getAllIncome(): Flow<List<IncomeEntry>>
    fun getTotalIncomeBetweenDates(startDate: Long, endDate: Long): Flow<Double?>
    fun getTotalIncomeForCow(cowId: Long): Flow<Double?>
}

class IncomeRepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao
) : IncomeRepository {
    override suspend fun insertIncome(incomeEntry: IncomeEntry) {
        incomeDao.insert(incomeEntry)
    }

    override suspend fun updateIncome(incomeEntry: IncomeEntry) {
        incomeDao.update(incomeEntry)
    }

    override suspend fun deleteIncome(incomeEntry: IncomeEntry) {
        incomeDao.delete(incomeEntry)
    }

    override fun getAllIncome(): Flow<List<IncomeEntry>> {
        return incomeDao.getAllIncome()
    }

    override fun getTotalIncomeBetweenDates(startDate: Long, endDate: Long): Flow<Double?> {
        return incomeDao.getTotalIncomeBetweenDates(startDate, endDate)
    }

    override fun getTotalIncomeForCow(cowId: Long): Flow<Double?> {
        return incomeDao.getTotalIncomeForCow(cowId)
    }
}
