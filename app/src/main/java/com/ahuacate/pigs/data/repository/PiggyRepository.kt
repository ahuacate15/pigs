package com.ahuacate.pigs.data.repository

import androidx.annotation.WorkerThread
import com.ahuacate.pigs.data.dao.PiggyDao
import com.ahuacate.pigs.data.entity.PiggyEntity
import kotlinx.coroutines.flow.Flow

class PiggyRepository(private val piggyDao : PiggyDao) {

    val allPiggies : Flow<List<PiggyEntity>> = piggyDao.getAll();

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(piggyEntity: PiggyEntity) : Long {
        return piggyDao.insert(piggyEntity)
    }
}