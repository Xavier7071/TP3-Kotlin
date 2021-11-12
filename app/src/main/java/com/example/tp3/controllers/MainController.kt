package com.example.tp3.controllers

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.tp3.models.Statistics
import com.example.tp3.models.Users
import com.example.tp3.models.WordManager

class MainController private constructor() {
    private var statistics = Statistics()
    private var words = WordManager()
    private var userList = ArrayList<Users>()
    private var database: AppDatabase? = null

    private object HOLDER {
        val INSTANCE = MainController()
    }

    companion object {
        val instance: MainController by lazy { HOLDER.INSTANCE }
    }

    fun loadDatabase(applicationContext: Context) {
        userList.add(Users(1, "Xavier", "2048548", "Easy"))
        userList.add(Users(2, "Christopher", "Masse", "Easy"))
        userList.add(Users(3, "Admin", "admin", "Easy"))

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        try {
            database!!.usersDAO().insertAll(userList)
        } catch (ex: Exception) {
            Log.d("logdemo", "Records already in Database: ${ex.message}")
        }
    }

    fun initializeEasyWords() {
        words.words.add("test")
        words.words.add("apple")
        words.words.add("math")
        words.words.add("monkey")
        words.words.add("peach")
        words.words.add("video")
        words.words.add("movie")
        words.words.add("game")
        words.words.add("orange")
        words.words.add("player")
    }

    fun initializeHardWords() {
        words.words.add("science")
        words.words.add("ancient")
        words.words.add("computer")
        words.words.add("programming")
        words.words.add("multiple")
        words.words.add("increment")
        words.words.add("monster")
        words.words.add("headphone")
        words.words.add("manager")
        words.words.add("fortnite")
    }

    fun incrementScore() {
        statistics.score += 10
    }

    fun decreaseScore() {
        if (statistics.score <= 1) {
            statistics.score = 0
        } else {
            statistics.score -= 2
        }
    }

    fun incrementWordsDone() {
        statistics.wordsDone += 1
    }

    fun getScore(): Int {
        return statistics.score
    }

    fun getWordsDone(): Int {
        return statistics.wordsDone
    }

    fun updateId(id: Int) {
        statistics.id = id
    }

    fun getId(): Int {
        return statistics.id
    }

    fun getScrambledWord(): String {
        return words.scrambledWord
    }

    fun getCorrectWord(): String {
        return words.correctWord
    }

    fun chooseRandomWord() {
        words.chooseRandomWord()
    }

    fun getDatabase(): AppDatabase? {
        return database
    }

    fun insertUser(username: String, password: String) {
        database!!.usersDAO().insert(Users((database!!.usersDAO().findAll().lastIndex + 2), username, password, "Easy"))
    }
}