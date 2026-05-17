package com.ksheerasagara.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ksheerasagara.app.data.local.entity.Cow
import kotlinx.coroutines.flow.Flow

@Dao
interface CowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cow: Cow)

    @Update
    suspend fun update(cow: Cow)

    @Delete
    suspend fun delete(cow: Cow)

    @Query("SELECT * FROM cows ORDER BY name ASC")
    fun getAllCows(): Flow<List<Cow>>

    @Query("SELECT * FROM cows WHERE cowId = :id")
    fun getCowById(id: Long): Flow<Cow?>
}
