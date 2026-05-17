package com.ksheerasagara.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ksheerasagara.app.data.local.entity.ExpenseEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseEntry: ExpenseEntry)

    @Update
    suspend fun update(expenseEntry: ExpenseEntry)

    @Delete
    suspend fun delete(expenseEntry: ExpenseEntry)

    @Query("SELECT * FROM expense_entries ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntry>>

    @Query("SELECT SUM(amount) FROM expense_entries WHERE date >= :startDate AND date <= :endDate")
    fun getTotalExpenseBetweenDates(startDate: Long, endDate: Long): Flow<Double?>

    @Query("SELECT SUM(amount) FROM expense_entries WHERE cowId = :cowId")
    fun getTotalExpenseForCow(cowId: Long): Flow<Double?>

    @Query("SELECT category, SUM(amount) as total FROM expense_entries WHERE date >= :startDate AND date <= :endDate GROUP BY category")
    fun getExpensesByCategory(startDate: Long, endDate: Long): Flow<List<ExpenseCategoryTotal>>
}

data class ExpenseCategoryTotal(
    val category: String,
    val total: Double
)
