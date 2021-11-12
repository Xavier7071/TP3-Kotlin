package com.example.tp3.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tp3.R
import com.example.tp3.controllers.MainController

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        MainController.instance.loadDatabase(applicationContext)

        val connectBtn = findViewById<Button>(R.id.connect_button)
        connectBtn.setOnClickListener { connection() }
        val createBtn = findViewById<Button>(R.id.create_button)
        createBtn.setOnClickListener { createAccount() }
    }

    private fun connection() {
        val username = findViewById<View>(R.id.username_input) as EditText
        val password = findViewById<View>(R.id.password_input) as EditText
        val users = MainController.instance.getDatabase()!!.usersDAO().findAll()
        if (username.text.toString().isEmpty() || password.text.toString().isEmpty()) {
            Toast.makeText(
                this, "Login information needs to be filled",
                Toast.LENGTH_SHORT
            ).show()
        } else if (users.find { it.name == username.text.toString() } == null) {
            Toast.makeText(
                this, "Login information invalid",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val user = users.find { it.name == username.text.toString() }
            if (user!!.password != password.text.toString()) {
                Toast.makeText(
                    this, "Login information invalid",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                MainController.instance.updateId(MainController.instance.getDatabase()!!.usersDAO().findByName(username.text.toString()).id)
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createAccount() {
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }
}