package com.example.tp3.controllers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp3.models.Users
import com.example.tp3.data.UserDatabase

@Database(entities = [Users::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDAO() : UserDatabase
}