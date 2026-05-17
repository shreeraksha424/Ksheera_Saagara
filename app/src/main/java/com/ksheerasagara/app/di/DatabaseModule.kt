package com.ksheerasagara.app.di

import android.content.Context
import androidx.room.Room
import com.ksheerasagara.app.data.local.AppDatabase
import com.ksheerasagara.app.data.local.dao.CowDao
import com.ksheerasagara.app.data.local.dao.ExpenseDao
import com.ksheerasagara.app.data.local.dao.IncomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ksheera_sagara_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideIncomeDao(appDatabase: AppDatabase): IncomeDao = appDatabase.incomeDao()

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao = appDatabase.expenseDao()

    @Provides
    fun provideCowDao(appDatabase: AppDatabase): CowDao = appDatabase.cowDao()
}
