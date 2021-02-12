package com.ahuacate.pigs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ahuacate.pigs.data.dao.PiggyDao
import com.ahuacate.pigs.data.dao.SavingDao
import com.ahuacate.pigs.data.dao.SavingDetailDao
import com.ahuacate.pigs.data.entity.PiggyEntity
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import com.ahuacate.pigs.data.entity.SavingEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PiggyEntity::class, SavingEntity::class, SavingDetailEntity::class], version = 5, exportSchema = false )
public abstract class PiggyRoomDB : RoomDatabase() {

    abstract fun piggyDao() : PiggyDao
    abstract fun savingDao() : SavingDao
    abstract fun savingDetailDao() : SavingDetailDao


    private class PiggyRoomDBCallback(
        private val scope : CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            /* to populate database */
            INSTANCE?.let {
                database -> scope.launch {

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
                    //.addCallback(PiggyRoomDBCallback(scope))
                    .fallbackToDestructiveMigrationFrom(1,2)
                    .fallbackToDestructiveMigrationFrom(2,3)
                    .fallbackToDestructiveMigrationFrom(3,4)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}