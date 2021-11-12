package com.example.tp3.data

import androidx.room.*
import com.example.tp3.models.Users

@Dao
interface UserDatabase {
    @Query("SELECT * FROM Users")
    fun findAll() : List<Users>

    @Query("SELECT * FROM Users WHERE name IN (:name)")
    fun findByName(name: String): Users

    @Query("SELECT * FROM Users WHERE id IN (:id)")
    fun findById(id: Int): Users

    @Insert
    fun insertAll(vararg user : Users)

    @Insert
    fun insertAll(users : List<Users>)

    @Delete
    fun delete(user: Users)

    @Insert
    fun insert(user : Users)

    @Update
    fun update(user : Users)
}