package com.ahuacate.pigs.data

import android.app.Application
import com.ahuacate.pigs.data.repository.SavingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PiggyApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PiggyRoomDB.getDataBase(this, applicationScope) }
    val repository by lazy { SavingRepository(database.savingDao()) }

}