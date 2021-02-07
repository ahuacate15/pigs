package com.ahuacate.pigs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingDetailDao {

    @Insert
    suspend fun insertAll(listDetail : List<SavingDetailEntity>)


    @Query("SELECT * FROM saving_detail WHERE id_saving = :id ORDER BY sequence")
    fun findByIdSaving(id : Int?) : Flow<MutableList<SavingDetailEntity>>

    @Query("UPDATE saving_detail SET selected = :selected WHERE sequence = :sequence AND id_saving = :idSaving ")
    suspend fun update(selected : Boolean, sequence : Int?, idSaving : Long) : Int
}