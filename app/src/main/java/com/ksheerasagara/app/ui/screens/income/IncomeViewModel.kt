package com.ksheerasagara.app.ui.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.data.local.entity.IncomeEntry
import com.ksheerasagara.app.data.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    val allIncome: StateFlow<List<IncomeEntry>> = incomeRepository.getAllIncome()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertIncome(
        date: Long,
        cowId: Long?,
        milkLiters: Double,
        fatPercent: Double,
        snfPercent: Double,
        paymentAmount: Double,
        notes: String
    ) {
        viewModelScope.launch {
            val entry = IncomeEntry(
                date = date,
                cowId = cowId,
                milkLiters = milkLiters,
                fatPercent = fatPercent,
                snfPercent = snfPercent,
                paymentAmount = paymentAmount,
                notes = notes
            )
            incomeRepository.insertIncome(entry)
        }
    }

    fun deleteIncome(entry: IncomeEntry) {
        viewModelScope.launch {
            incomeRepository.deleteIncome(entry)
        }
    }
}
