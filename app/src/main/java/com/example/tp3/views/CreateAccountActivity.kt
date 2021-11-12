package com.example.tp3.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3.R
import com.example.tp3.controllers.MainController

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val createBtn = findViewById<Button>(R.id.create_button)
        createBtn.setOnClickListener { createAccount() }
    }

    private fun createAccount() {
        val username = findViewById<EditText>(R.id.username_input)
        val password = findViewById<EditText>(R.id.password_input)
        val users = MainController.instance.getDatabase()!!.usersDAO().findAll()
        if (username.text.toString().isEmpty() || password.text.toString().isEmpty()) {
            Toast.makeText(
                this, "Information needs to be filled",
                Toast.LENGTH_SHORT
            ).show()
        } else if (users.find { it.name == username.text.toString() } != null) {
            Toast.makeText(
                this, "An account with the same username exists",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            MainController.instance.insertUser(username.text.toString(), password.text.toString())
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}