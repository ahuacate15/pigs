package com.ahuacate.pigs.data

import android.app.Application
import com.ahuacate.pigs.data.repository.SavingDetailRepository
import com.ahuacate.pigs.data.repository.SavingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PiggyApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { PiggyRoomDB.getDataBase(this, applicationScope) }

    val repository by lazy { SavingRepository(database.savingDao()) }
    val detailRepository by lazy { SavingDetailRepository(database.savingDetailDao()) }

}