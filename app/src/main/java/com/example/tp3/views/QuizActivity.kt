package com.example.tp3.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3.R
import com.example.tp3.controllers.MainController
import com.example.tp3.models.Users
import java.text.SimpleDateFormat
import java.util.*

class QuizActivity : AppCompatActivity() {
    private var nbAttempts = 1
    private var user: Users? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val submitBtn = findViewById<Button>(R.id.submit_button)
        submitBtn.setOnClickListener { validateWord() }
        val skipBtn = findViewById<Button>(R.id.skip_button)
        skipBtn.setOnClickListener { runQuestion() }

        user = MainController.instance.getDatabase()!!.databaseDAO()
            .findUserById(MainController.instance.getId())

        MainController.instance.resetQuiz()
        updateNbAttempts()
        updateScore()

        if (MainController.instance.getDatabase()!!.databaseDAO()
                .findUserById(MainController.instance.getId()).difficulty == "Easy"
        ) {
            MainController.instance.initializeEasyWords()
        } else {
            MainController.instance.initializeHardWords()
        }
        runQuestion()
    }

    private fun updateScore() {
        (findViewById<TextView>(R.id.score_view)).text =
            "Score: ${MainController.instance.getScore()}"
    }

    private fun updateWordsDone() {
        (findViewById<TextView>(R.id.nbWord_view)).text =
            "${MainController.instance.getWordsDone()} of 10 words"
    }

    private fun updateNbAttempts() {
        (findViewById<TextView>(R.id.nbAttempts_view)).text = "Attempt #${nbAttempts} of 5 attempts"
    }

    private fun displayScrambledWord() {
        (findViewById<TextView>(R.id.word_view)).text = MainController.instance.getScrambledWord()
    }

    private fun runQuestion() {
        MainController.instance.incrementWordsDone()
        if (MainController.instance.getWordsDone() < 11) {
            MainController.instance.chooseRandomWord()
            displayScrambledWord()
            updateWordsDone()
            nbAttempts = 1
            updateNbAttempts()
        } else {
            saveStatistics()
        }
    }

    private fun validateWord() {
        val userInput = findViewById<EditText>(R.id.text_input).text.toString()
        if (userInput == MainController.instance.getCorrectWord()) {
            MainController.instance.incrementScore()
            updateScore()
            runQuestion()
        } else {
            MainController.instance.decreaseScore()
            updateScore()
            nbAttempts++
            if (nbAttempts > 5) {
                runQuestion()
            } else {
                updateNbAttempts()
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveStatistics() {
        val timeZone = TimeZone.getTimeZone("America/Montreal")
        val calendar = Calendar.getInstance(timeZone)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val simpleDateFormat = SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.CANADA)
        simpleDateFormat.timeZone = timeZone
        MainController.instance.insertStats(
            user!!.name,
            MainController.instance.getScore(),
            SimpleDateFormat(
                "EE MMM dd HH:mm:ss zzz yyyy",
                Locale.CANADA
            ).parse(simpleDateFormat.format(calendar.time))
        )

        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}