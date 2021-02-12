package com.ahuacate.pigs.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saving")
class SavingEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_saving")
    var idSaving : Int? = null,

    @ColumnInfo(name = "title")
    var title : String? = null,

    @ColumnInfo(name = "real_amount")
    var realAmount : Int,

    @ColumnInfo(name = "aprox_amount")
    var aproxAmount : Int,

    @ColumnInfo(name = "description")
    var description : String? = null,

    @ColumnInfo(name = "acumulated_amount")
    var acumulatedAmount : Int
)