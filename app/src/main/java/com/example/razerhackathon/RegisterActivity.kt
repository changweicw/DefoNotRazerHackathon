package com.example.razerhackathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.razerhackathon.global.constants

import com.example.razerhackathon.global.redirectPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {



    // Textbox Variables
    private lateinit var editTextNric : EditText
    private lateinit var editTextNricExp : EditText
    private lateinit var editTextFirstName : EditText
    private lateinit var editTextLastName : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        // Getting all the UI elements
        editTextNric = findViewById(R.id.editTextNric)
        editTextNricExp = findViewById(R.id.editTextNricExp)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        val returnToLogin = findViewById<ImageButton>(R.id.backToLogin)

        returnToLogin.setOnClickListener {
            val tempIntent = Intent(it.context, LandingActivity::class.java)
            startActivity(tempIntent)
        }
    }

    fun buttonNextOnClick(view: View) {
        val nric = editTextNric.text.toString()
        val nricExp = editTextNricExp.text.toString()
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()

        validateEntry()
        val intent = redirectPage.register2Activity(this)
        intent.putExtra(constants.NRIC, nric)
        intent.putExtra(constants.NRIC_EXP, nricExp)
        intent.putExtra(constants.FIRST_NAME, firstName)
        intent.putExtra(constants.LAST_NAME, lastName)
        startActivity(intent)
    }

    // Validate the entry to see if it is acceptable.
    fun validateEntry(){
        // Todo Validate entries.
    }




}
