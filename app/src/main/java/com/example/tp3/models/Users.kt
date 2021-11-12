package com.example.tp3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Users(@PrimaryKey val id: Int, @ColumnInfo(name="name") var name : String, @ColumnInfo(name = "password") var password : String,  @ColumnInfo(name = "difficulty") var difficulty : String)
