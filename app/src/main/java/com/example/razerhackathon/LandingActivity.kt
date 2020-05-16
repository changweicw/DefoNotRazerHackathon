package com.example.razerhackathon

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()

        val loginButton = findViewById<Button>(R.id.buttonSignIn)
        loginButton.setOnClickListener {
            val tempIntent = Intent(it.context, SignInActivity::class.java)
            startActivity(tempIntent)
        }
        val registerButton = findViewById<Button>(R.id.buttonCreateAccount)
        registerButton.setOnClickListener {
            val tempIntent = Intent(it.context, RegisterActivity::class.java)
            startActivity(tempIntent)
        }
    }
}