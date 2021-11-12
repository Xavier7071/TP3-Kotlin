package com.example.tp3.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tp3.R
import com.example.tp3.controllers.MainController

class QuizActivity : AppCompatActivity() {
    private var nbAttempts = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val submitBtn = findViewById<Button>(R.id.submitBtn)
        submitBtn.setOnClickListener { validateWord() }
        val skipBtn = findViewById<Button>(R.id.skipBtn)
        skipBtn.setOnClickListener { runQuestion() }

        updateNbAttempts()
        updateScore()
        println(MainController.instance.getDatabase()!!.usersDAO().findById(MainController.instance.getId()))
        if (MainController.instance.getDatabase()!!.usersDAO().findById(MainController.instance.getId()).difficulty == "Easy") {
            MainController.instance.initializeEasyWords()
        } else {
            MainController.instance.initializeHardWords()
        }
        runQuestion()
    }

    private fun updateScore() {
        (findViewById<View>(R.id.scoreView) as TextView).text = "Score: ${MainController.instance.getScore()}"
    }

    private fun updateWordsDone() {
        (findViewById<View>(R.id.nbWordsView) as TextView).text = "${MainController.instance.getWordsDone()} of 10 words"
    }

    private fun updateNbAttempts() {
        (findViewById<View>(R.id.nbAttemptsView) as TextView).text = "Attempt #${nbAttempts} of 5 attempts"
    }

    private fun displayScrambledWord() {
        (findViewById<View>(R.id.wordView) as TextView).text = MainController.instance.getScrambledWord()
    }

    private fun runQuestion() {
        MainController.instance.incrementWordsDone()
        if (MainController.instance.getWordsDone() < 11) {
            MainController.instance.chooseRandomWord()
            displayScrambledWord()
            updateWordsDone()
            nbAttempts = 1
            updateNbAttempts()
        }
    }

    private fun validateWord() {
        val userInput = findViewById<EditText>(R.id.textInput).text.toString()
        if (userInput == MainController.instance.getCorrectWord()) {
            MainController.instance.incrementScore()
            updateScore()
            runQuestion()
        } else {
            MainController.instance.decreaseScore()
            nbAttempts++
            if (nbAttempts > 5) {
                runQuestion()
            } else {
                updateNbAttempts()
                Toast.makeText(applicationContext, "Essayer encore", Toast.LENGTH_LONG).show()
            }
        }
    }
}