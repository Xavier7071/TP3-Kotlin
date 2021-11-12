package com.example.tp3.controllers

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tp3.models.GlobalStatistics
import com.example.tp3.models.Users

@Database(entities = [Users::class, GlobalStatistics::class], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDAO(): com.example.tp3.data.Database
}