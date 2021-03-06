package com.example.tp3.controllers

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.tp3.models.GlobalStatistics
import com.example.tp3.models.UserStatistics
import com.example.tp3.models.Users
import com.example.tp3.models.WordManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainController private constructor() {
    private var statistics = UserStatistics()
    private var words: WordManager? = null
    private var userList = ArrayList<Users>()
    private var globalStatistics = ArrayList<GlobalStatistics>()
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

        globalStatistics.add(
            GlobalStatistics(
                1,
                "Xavier",
                20,
                SimpleDateFormat("dd MM yyyy", Locale.CANADA).parse("10 11 2021")
            )
        )
        globalStatistics.add(
            GlobalStatistics(
                2,
                "Christopher",
                20,
                SimpleDateFormat("dd MM yyyy", Locale.CANADA).parse("12 10 2021")
            )
        )
        globalStatistics.add(
            GlobalStatistics(
                3,
                "Admin",
                10,
                SimpleDateFormat("dd MM yyyy", Locale.CANADA).parse("1 11 2021")
            )
        )

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        try {
            database!!.databaseDAO().insertAllUsers(userList)
        } catch (ex: Exception) {
            Log.d("logdemo", "Records already in Database: ${ex.message}")
        }
        try {
            database!!.databaseDAO().insertAllStatistics(globalStatistics)
        } catch (ex: Exception) {
            Log.d("logdemo", "Records already in Database: ${ex.message}")
        }
    }

    fun initializeEasyWords() {
        words = WordManager()
        words!!.words.add("test")
        words!!.words.add("apple")
        words!!.words.add("math")
        words!!.words.add("monkey")
        words!!.words.add("peach")
        words!!.words.add("video")
        words!!.words.add("movie")
        words!!.words.add("game")
        words!!.words.add("orange")
        words!!.words.add("player")
    }

    fun initializeHardWords() {
        words = WordManager()
        words!!.words.add("science")
        words!!.words.add("ancient")
        words!!.words.add("computer")
        words!!.words.add("programming")
        words!!.words.add("multiple")
        words!!.words.add("increment")
        words!!.words.add("monster")
        words!!.words.add("headphone")
        words!!.words.add("manager")
        words!!.words.add("fortnite")
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

    fun setWordsDone(wordsDone: Int) {
        statistics.wordsDone = wordsDone
    }

    fun setScore(score: Int) {
        statistics.score = score
    }

    fun resetQuiz() {
        statistics.wordsDone = 0
        statistics.score = 0
    }

    fun getScore(): Int {
        return statistics.score
    }

    fun getWordsDone(): Int {
        return statistics.wordsDone
    }

    fun setId(id: Int) {
        statistics.id = id
    }

    fun getId(): Int {
        return statistics.id
    }

    fun getScrambledWord(): String {
        return words!!.scrambledWord
    }

    fun setScrambledWord(scrambledWord: String) {
        words!!.scrambledWord = scrambledWord
    }

    fun getWords(): ArrayList<String> {
        return words!!.words
    }

    fun setWords(savedWords: ArrayList<String>) {
        words!!.words = savedWords
    }

    fun getRemovedWords(): ArrayList<String> {
        return words!!.removedWords
    }

    fun setRemovedWords(savedRemovedWords: ArrayList<String>) {
        words!!.removedWords = savedRemovedWords
    }

    fun getCorrectWord(): String {
        return words!!.correctWord
    }

    fun setCorrectWord(correctWord: String) {
        words!!.correctWord = correctWord
    }

    fun chooseRandomWord() {
        words!!.chooseRandomWord()
    }

    fun getDatabase(): AppDatabase? {
        return database
    }

    fun insertUser(username: String, password: String) {
        database!!.databaseDAO().insertUser(
            Users(
                (database!!.databaseDAO().findAllUsers().lastIndex + 2),
                username,
                password,
                "Easy"
            )
        )
    }

    fun insertStats(username: String, score: Int, date: Date?) {
        database!!.databaseDAO().insertStatistics(
            GlobalStatistics(
                (database!!.databaseDAO().findAllStatistics().lastIndex + 2),
                username,
                score,
                date
            )
        )
    }

    fun getAllLeaderboard(): ArrayList<GlobalStatistics> {
        globalStatistics.clear()
        globalStatistics.addAll(database!!.databaseDAO().findBestStatistics())
        return globalStatistics
    }

    fun get7DaysLeaderboard(): ArrayList<GlobalStatistics> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -5)
        calendar.add(Calendar.DAY_OF_YEAR, -7)

        globalStatistics.clear()
        globalStatistics.addAll(
            database!!.databaseDAO().findStatisticsWithFilter(
                calendar.time
            )
        )
        return globalStatistics
    }

    fun get30DaysLeaderboard(): ArrayList<GlobalStatistics> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -5)
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        globalStatistics.clear()
        globalStatistics.addAll(
            database!!.databaseDAO().findStatisticsWithFilter(
                calendar.time
            )
        )
        return globalStatistics
    }
}