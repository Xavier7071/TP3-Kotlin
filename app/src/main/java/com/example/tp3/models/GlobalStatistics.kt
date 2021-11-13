package com.example.tp3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.tp3.controllers.DateConverter
import java.util.*

@Entity(tableName = "Statistics")
data class GlobalStatistics(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "score") var score: Int,
    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter::class) var date: Date
)