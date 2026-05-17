package com.ksheerasagara.app.di

import com.ksheerasagara.app.data.repository.AnalyticsRepository
import com.ksheerasagara.app.data.repository.CowRepository
import com.ksheerasagara.app.data.repository.ExpenseRepository
import com.ksheerasagara.app.data.repository.IncomeRepository
import com.ksheerasagara.app.data.repository.AnalyticsRepositoryImpl
import com.ksheerasagara.app.data.repository.CowRepositoryImpl
import com.ksheerasagara.app.data.repository.ExpenseRepositoryImpl
import com.ksheerasagara.app.data.repository.IncomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIncomeRepository(
        incomeRepositoryImpl: IncomeRepositoryImpl
    ): IncomeRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun bindCowRepository(
        cowRepositoryImpl: CowRepositoryImpl
    ): CowRepository

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepository(
        analyticsRepositoryImpl: AnalyticsRepositoryImpl
    ): AnalyticsRepository
}
