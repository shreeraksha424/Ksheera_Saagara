package com.ksheerasagara.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ksheerasagara.app.data.local.entity.IncomeEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(incomeEntry: IncomeEntry)

    @Update
    suspend fun update(incomeEntry: IncomeEntry)

    @Delete
    suspend fun delete(incomeEntry: IncomeEntry)

    @Query("SELECT * FROM income_entries ORDER BY date DESC")
    fun getAllIncome(): Flow<List<IncomeEntry>>

    @Query("SELECT SUM(paymentAmount) FROM income_entries WHERE date >= :startDate AND date <= :endDate")
    fun getTotalIncomeBetweenDates(startDate: Long, endDate: Long): Flow<Double?>

    @Query("SELECT SUM(paymentAmount) FROM income_entries WHERE cowId = :cowId")
    fun getTotalIncomeForCow(cowId: Long): Flow<Double?>
}
