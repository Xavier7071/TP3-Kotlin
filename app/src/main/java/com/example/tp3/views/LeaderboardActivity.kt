package com.example.tp3.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.R
import com.example.tp3.adapter.LeaderboardAdapter
import com.example.tp3.controllers.MainController
import com.google.android.material.navigation.NavigationView

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val allButton = findViewById<RadioButton>(R.id.all_button)
        val sevenDaysButton = findViewById<RadioButton>(R.id.sevenDays_button)
        val thirtyDaysButton = findViewById<RadioButton>(R.id.thirtyDays_button)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)

        allButton.setOnClickListener { filterLeaderboard() }
        sevenDaysButton.setOnClickListener { filterLeaderboard() }
        thirtyDaysButton.setOnClickListener { filterLeaderboard() }
        allButton.isChecked = true
        filterLeaderboard()

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item))
            false
        else
            when (item.itemId) {
                else -> super.onOptionsItemSelected(item)
            }
    }

    private fun filterLeaderboard() {
        rvMain = findViewById(R.id.rvMain)
        when {
            findViewById<RadioButton>(R.id.all_button).isChecked -> {
                rvMain.adapter = LeaderboardAdapter(MainController.instance.getAllLeaderboard())
            }
            findViewById<RadioButton>(R.id.sevenDays_button).isChecked -> {
                rvMain.adapter = LeaderboardAdapter(MainController.instance.get7DaysLeaderboard())
            }
            else -> {
                rvMain.adapter = LeaderboardAdapter(MainController.instance.get30DaysLeaderboard())
            }
        }
        rvMain.layoutManager = LinearLayoutManager(this)
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
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
    }
}