package com.ksheerasagara.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_entries")
data class IncomeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long,
    val cowId: Long?,
    val milkLiters: Double,
    val fatPercent: Double,
    val snfPercent: Double,
    val paymentAmount: Double,
    val notes: String
)
