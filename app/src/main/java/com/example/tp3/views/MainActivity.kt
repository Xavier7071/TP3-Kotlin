package com.example.tp3.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tp3.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val intent = Intent(this, MainActivity::class.java)
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
}