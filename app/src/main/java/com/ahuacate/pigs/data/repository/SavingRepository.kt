package com.ahuacate.pigs.data.repository

import androidx.annotation.WorkerThread
import com.ahuacate.pigs.data.dao.SavingDao
import com.ahuacate.pigs.data.entity.SavingEntity
import kotlinx.coroutines.flow.Flow

class SavingRepository(private val savingDao: SavingDao) {

    var allSaving : Flow<List<SavingEntity>> = savingDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(savingEntity : SavingEntity) : Long {
        return savingDao.insert(savingEntity)
    }


    fun findById(id : Int) : Flow<SavingEntity> {
        return savingDao.findById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(idSaving : Int, acumulatedAmount : Int) {
        return savingDao.updateAccumulate(idSaving, acumulatedAmount)
    }
}