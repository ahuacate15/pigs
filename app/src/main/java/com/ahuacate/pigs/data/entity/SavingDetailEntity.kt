package com.ahuacate.pigs.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "saving_detail")
class SavingDetailEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = null,

    var sequence : Int,
    var selected : Boolean,

    @ColumnInfo(name = "id_saving")
    @ForeignKey(
        entity = SavingEntity::class,
        parentColumns = ["idSaving"],
        childColumns = ["idSaving"]
    )
    var idSaving : Long
)