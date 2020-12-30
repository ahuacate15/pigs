package com.ahuacate.pigs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahuacate.pigs.data.dao.PiggyDao
import com.ahuacate.pigs.data.entity.PiggyEntity

@Database(entities = [PiggyEntity::class], version = 1, exportSchema = false )
public abstract class PiggyRoomDB : RoomDatabase() {

    abstract fun piggyDao() : PiggyDao

    companion object {

        @Volatile
        private var INSTANCE : PiggyRoomDB? = null

        fun getDataBase(context : Context) : PiggyRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PiggyRoomDB::class.java,
                    "piggy_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}