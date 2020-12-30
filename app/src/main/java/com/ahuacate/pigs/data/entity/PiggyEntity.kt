package com.ahuacate.pigs.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "piggy")
class PiggyEntity(

    @PrimaryKey
    @ColumnInfo(name = "code_piggy")
    val codePiggy : String,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "history")
    val history : String

)