package com.ksheerasagara.app.ui.screens.cow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksheerasagara.app.data.local.entity.Cow
import com.ksheerasagara.app.data.repository.CowRepository
import com.ksheerasagara.app.data.repository.ExpenseRepository
import com.ksheerasagara.app.data.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CowViewModel @Inject constructor(
    private val cowRepository: CowRepository,
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    val allCows: StateFlow<List<Cow>> = cowRepository.getAllCows()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertCow(
        name: String,
        breed: String,
        age: Int,
        purchaseCost: Double,
        notes: String
    ) {
        viewModelScope.launch {
            val cow = Cow(
                name = name,
                breed = breed,
                age = age,
                purchaseCost = purchaseCost,
                notes = notes
            )
            cowRepository.insertCow(cow)
        }
    }

    fun deleteCow(cow: Cow) {
        viewModelScope.launch {
            cowRepository.deleteCow(cow)
        }
    }

    fun getCowIncome(cowId: Long): StateFlow<Double> {
        return incomeRepository.getTotalIncomeForCow(cowId)
            .map { it ?: 0.0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    }

    fun getCowExpense(cowId: Long): StateFlow<Double> {
        return expenseRepository.getTotalExpenseForCow(cowId)
            .map { it ?: 0.0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    }
}
