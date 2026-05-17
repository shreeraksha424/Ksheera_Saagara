package com.ksheerasagara.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_entries")
data class ExpenseEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long,
    val category: String, // Fodder, Medical, Labor, Electricity, Vet, Miscellaneous
    val amount: Double,
    val description: String,
    val cowId: Long?
)
