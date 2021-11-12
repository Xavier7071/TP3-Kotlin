package com.example.tp3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Statistics")
data class Leaderboard(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "score") var score: Int,
    @ColumnInfo(name = "date") var date: String
)