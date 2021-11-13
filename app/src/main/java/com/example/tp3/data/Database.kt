package com.example.tp3.data

import androidx.room.*
import com.example.tp3.models.GlobalStatistics
import com.example.tp3.models.Users
import java.util.*

@Dao
interface Database {
    @Query("SELECT * FROM Users")
    fun findAllUsers(): List<Users>

    @Query("SELECT * FROM Users WHERE name IN (:name)")
    fun findUserByName(name: String): Users

    @Query("SELECT * FROM Users WHERE id IN (:id)")
    fun findUserById(id: Int): Users

    @Insert
    fun insertAllUsers(vararg user: Users)

    @Insert
    fun insertAllUsers(users: List<Users>)

    @Delete
    fun deleteUser(user: Users)

    @Insert
    fun insertUser(user: Users)

    @Update
    fun updateUser(user: Users)

    @Query("SELECT * FROM Statistics")
    fun findAllStatistics(): List<GlobalStatistics>

    @Query("SELECT * FROM Statistics ORDER BY score DESC, date DESC LIMIT 10")
    fun findBestStatistics(): List<GlobalStatistics>

    @Query("SELECT * FROM Statistics WHERE date >= :date ORDER BY score DESC, date DESC LIMIT 10")
    fun findStatisticsWithFilter(date: Date?): List<GlobalStatistics>

    @Query("SELECT * FROM Statistics WHERE id IN (:id)")
    fun findStatisticsById(id: Int): GlobalStatistics

    @Insert
    fun insertStatistics(statistics: GlobalStatistics)

    @Insert
    fun insertAllStatistics(statistics: List<GlobalStatistics>)
}