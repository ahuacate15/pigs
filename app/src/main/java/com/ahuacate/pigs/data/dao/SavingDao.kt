package com.ahuacate.pigs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ahuacate.pigs.data.entity.SavingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingDao {

    @Query("SELECT * FROM saving ORDER BY id_saving DESC")
    fun getAll() : Flow<List<SavingEntity>>

    @Insert
    suspend fun insert(savingEntity: SavingEntity) : Long

    @Query("DELETE FROM saving")
    suspend fun deleteAll()

    @Query("SELECT * FROM saving WHERE id_saving = :idSaving")
    fun findById(idSaving : Int) : Flow<SavingEntity>

    @Query("UPDATE saving SET acumulated_amount = acumulated_amount + :acumulatedAmount WHERE id_saving = :idSaving")
    suspend fun updateAccumulate(idSaving : Int, acumulatedAmount : Int)
}