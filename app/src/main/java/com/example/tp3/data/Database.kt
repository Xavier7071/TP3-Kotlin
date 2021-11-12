package com.example.tp3.data

import androidx.room.*
import com.example.tp3.models.Leaderboard
import com.example.tp3.models.Users

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
    fun findAllStatistics(): List<Leaderboard>

    @Query("SELECT * FROM Statistics WHERE name IN (:name)")
    fun findStatisticsByName(name: String): Leaderboard

    @Query("SELECT * FROM Statistics WHERE id IN (:id)")
    fun findStatisticsById(id: Int): Leaderboard

    @Insert
    fun insertAllStatistics(vararg leaderboard: Leaderboard)

    @Insert
    fun insertAllStatistics(leaderboard: List<Leaderboard>)

    @Delete
    fun deleteStatistics(statistics: Leaderboard)

    @Insert
    fun insertStatistics(statistics: Leaderboard)

    @Update
    fun updateStatistics(statistics: Leaderboard)
}