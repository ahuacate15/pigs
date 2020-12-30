package com.ahuacate.pigs.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saving")
class SavingEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_saving")
    var idSaving : Integer,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "real_amount")
    val realAmount : Double,

    @ColumnInfo(name = "aprox_amount")
    val aproxAmount : Double
)