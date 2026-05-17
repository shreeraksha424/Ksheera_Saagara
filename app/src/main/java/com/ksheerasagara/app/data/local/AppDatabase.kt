package com.ksheerasagara.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ksheerasagara.app.data.local.dao.CowDao
import com.ksheerasagara.app.data.local.dao.ExpenseDao
import com.ksheerasagara.app.data.local.dao.IncomeDao
import com.ksheerasagara.app.data.local.entity.Cow
import com.ksheerasagara.app.data.local.entity.ExpenseEntry
import com.ksheerasagara.app.data.local.entity.IncomeEntry

@Database(
    entities = [IncomeEntry::class, ExpenseEntry::class, Cow::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun cowDao(): CowDao
}
