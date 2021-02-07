package com.ahuacate.pigs.data.repository

import androidx.annotation.WorkerThread
import com.ahuacate.pigs.data.dao.SavingDetailDao
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import kotlinx.coroutines.flow.Flow

class SavingDetailRepository(private val savingDetailDao: SavingDetailDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(listDetail : List<SavingDetailEntity>) {
        savingDetailDao.insertAll(listDetail)
    }

    fun findByIdSaving(id : Int?) : Flow<MutableList<SavingDetailEntity>> {
        return savingDetailDao.findByIdSaving(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(selected : Boolean, sequence : Int?, idSaving : Long) : Int {
        return savingDetailDao.update(selected, sequence, idSaving)
    }

}