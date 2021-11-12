package com.example.tp3.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tp3.R
import com.example.tp3.controllers.MainController
import com.example.tp3.models.Users
import com.google.android.material.navigation.NavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private var user: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener { saveChanges() }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> goToProfile()
                R.id.nav_bye -> disconnect()
                R.id.sub1 -> goToQuiz()
                R.id.sub2 -> goToLeaderboard()
            }
            true
        }

        user = MainController.instance.getDatabase()!!.usersDAO().findById(MainController.instance.getId())
        setRadioButtons()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item))
            false
        else
            when (item.itemId) {
                else -> super.onOptionsItemSelected(item)
            }
    }

    private fun goToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun disconnect() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToQuiz() {
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    private fun goToLeaderboard() {
        TODO("Not yet implemented")
    }

    private fun saveChanges() {
        val newPassword = findViewById<View>(R.id.newPassword_input) as EditText
    }

    private fun setRadioButtons() {
        if (user!!.difficulty == "Easy") {
            findViewById<RadioButton>(R.id.easy_button).isChecked = true
        } else {
            findViewById<RadioButton>(R.id.hard_button).isChecked = true
        }
    }
}