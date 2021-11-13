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
import java.util.*

class QuizActivity : AppCompatActivity() {
    private var nbAttempts = 1
    private var user: Users? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState != null) {
            loadData(savedInstanceState)
        } else {
            startGame()
        }

        val submitBtn = findViewById<Button>(R.id.submit_button)
        submitBtn.setOnClickListener { validateWord() }
        val skipBtn = findViewById<Button>(R.id.skip_button)
        skipBtn.setOnClickListener { runQuestion() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("NBATTEMPTS", nbAttempts)
        outState.putInt("SCORE", MainController.instance.getScore())
        outState.putInt("ID", MainController.instance.getId())
        outState.putInt("WORDSDONE", MainController.instance.getWordsDone())
        outState.putString("SCRAMBLEDWORD", MainController.instance.getScrambledWord())
        outState.putString("CORRECTWORD", MainController.instance.getCorrectWord())
        outState.putStringArrayList("WORDS", MainController.instance.getWords())
        outState.putStringArrayList("REMOVEDWORDS", MainController.instance.getRemovedWords())
        super.onSaveInstanceState(outState)
    }

    private fun loadData(savedInstanceState: Bundle?) {
        nbAttempts = savedInstanceState!!.getInt("NBATTEMPTS")
        MainController.instance.setScore(savedInstanceState.getInt("SCORE"))
        MainController.instance.setId(savedInstanceState.getInt("ID"))
        MainController.instance.setWordsDone(savedInstanceState.getInt("WORDSDONE"))
        MainController.instance.setScrambledWord(savedInstanceState.getString("SCRAMBLEDWORD")!!)
        MainController.instance.setCorrectWord(savedInstanceState.getString("CORRECTWORD")!!)
        MainController.instance.setWords(savedInstanceState.getStringArrayList("WORDS")!!)
        MainController.instance.setRemovedWords(savedInstanceState.getStringArrayList("REMOVEDWORDS")!!)
        user = MainController.instance.getDatabase()!!.databaseDAO().findUserById(MainController.instance.getId())
        updateNbAttempts()
        updateScore()
        updateWordsDone()
        displayScrambledWord()
    }

    private fun startGame() {
        MainController.instance.resetQuiz()
        user = MainController.instance.getDatabase()!!.databaseDAO()
            .findUserById(MainController.instance.getId())
        if (MainController.instance.getDatabase()!!.databaseDAO()
                .findUserById(MainController.instance.getId()).difficulty == "Easy"
        ) {
            MainController.instance.initializeEasyWords()
        } else {
            MainController.instance.initializeHardWords()
        }
        runQuestion()
        updateNbAttempts()
        updateScore()
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
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -5)
        MainController.instance.insertStats(
            user!!.name,
            MainController.instance.getScore(),
            calendar.time
        )

        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}