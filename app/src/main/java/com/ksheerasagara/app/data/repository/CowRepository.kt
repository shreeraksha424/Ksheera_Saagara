package com.ksheerasagara.app.data.repository

import com.ksheerasagara.app.data.local.dao.CowDao
import com.ksheerasagara.app.data.local.entity.Cow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CowRepository {
    suspend fun insertCow(cow: Cow)
    suspend fun updateCow(cow: Cow)
    suspend fun deleteCow(cow: Cow)
    fun getAllCows(): Flow<List<Cow>>
    fun getCowById(id: Long): Flow<Cow?>
}

class CowRepositoryImpl @Inject constructor(
    private val cowDao: CowDao
) : CowRepository {
    override suspend fun insertCow(cow: Cow) {
        cowDao.insert(cow)
    }

    override suspend fun updateCow(cow: Cow) {
        cowDao.update(cow)
    }

    override suspend fun deleteCow(cow: Cow) {
        cowDao.delete(cow)
    }

    override fun getAllCows(): Flow<List<Cow>> {
        return cowDao.getAllCows()
    }

    override fun getCowById(id: Long): Flow<Cow?> {
        return cowDao.getCowById(id)
    }
}
