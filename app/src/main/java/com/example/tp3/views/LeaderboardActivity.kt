package com.example.tp3.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.R
import com.example.tp3.adapter.LeaderboardAdapter
import com.example.tp3.controllers.MainController
import com.google.android.material.navigation.NavigationView

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var rvMain : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        rvMain = findViewById(R.id.rvMain)
        rvMain.adapter = LeaderboardAdapter(MainController.instance.getLeaderboard())

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)

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