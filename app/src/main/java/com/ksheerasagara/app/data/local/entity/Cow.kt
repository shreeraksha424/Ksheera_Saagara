package com.ksheerasagara.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cows")
data class Cow(
    @PrimaryKey(autoGenerate = true)
    val cowId: Long = 0,
    val name: String,
    val breed: String,
    val age: Int,
    val purchaseCost: Double,
    val notes: String
)
