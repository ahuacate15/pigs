package com.ahuacate.pigs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ahuacate.pigs.data.dao.PiggyDao
import com.ahuacate.pigs.data.dao.SavingDao
import com.ahuacate.pigs.data.entity.PiggyEntity
import com.ahuacate.pigs.data.entity.SavingEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PiggyEntity::class, SavingEntity::class], version = 2, exportSchema = false )
public abstract class PiggyRoomDB : RoomDatabase() {

    abstract fun piggyDao() : PiggyDao
    abstract fun savingDao() : SavingDao


    private class PiggyRoomDBCallback(
        private val scope : CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            /* to populate database */
            INSTANCE?.let {
                database -> scope.launch {
                    var savingDao = database.savingDao()

                    //delete all content of saving
                    savingDao.deleteAll()

                    //add sample savings
                    var saving = SavingEntity()
                    saving.title = "Viaje a madrid"
                    saving.realAmount = 5000
                    saving.aproxAmount = 5050
                    saving.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor"
                    savingDao.insert(saving)

                    var savingTwo = SavingEntity()
                    savingTwo.title = "Macbock AIR"
                    savingTwo.realAmount = 5000
                    savingTwo.aproxAmount = 5050
                    savingTwo.description = "Quis nostrud exercitation ullamco laboris nisi ut aliquip"
                    savingDao.insert(savingTwo)

                    var savingThree = SavingEntity()
                    savingThree.title = "Apartamento en ciudad merliot"
                    savingThree.realAmount = 200
                    savingThree.aproxAmount = 240
                    savingThree.description = "Duis aute irure dolor in"
                    savingDao.insert(savingThree)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE : PiggyRoomDB? = null

        fun getDataBase(context : Context, scope : CoroutineScope) : PiggyRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PiggyRoomDB::class.java,
                    "piggy_db"
                )
                    .addCallback(PiggyRoomDBCallback(scope))
                    //.fallbackToDestructiveMigrationFrom(1,2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}