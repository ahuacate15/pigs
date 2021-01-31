package com.ahuacate.pigs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingDetailDao {

    @Insert
    suspend fun insertAll(listDetail : List<SavingDetailEntity>)


    @Query("SELECT * FROM saving_detail WHERE id_saving = :id ORDER BY sequence")
    fun findByIdSaving(id : Int?) : Flow<List<SavingDetailEntity>>
}