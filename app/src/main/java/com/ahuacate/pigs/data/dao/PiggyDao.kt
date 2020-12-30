package com.ahuacate.pigs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahuacate.pigs.data.entity.PiggyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PiggyDao {

    @Query("SELECT * FROM piggy ORDER BY NAME")
    fun getAll() : Flow<List<PiggyEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(piggyEntity: PiggyEntity)
}