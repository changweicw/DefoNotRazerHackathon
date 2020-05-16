package com.example.razerhackathon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.razerhackathon.Models.ClientInfo

import com.example.razerhackathon.db.expeditionDAO

import com.example.razerhackathon.OkHttp.OkHttpRequestHandler

import com.example.razerhackathon.db.userDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.sharedPref
import com.example.razerhackathon.global.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register2.*

class Register2Activity : AppCompatActivity() {

    // Firebase Auth
    private lateinit var auth: FirebaseAuth

    // UI Lateinit variables
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var editTextCfmPassword : EditText

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        supportActionBar?.hide()
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextCfmPassword = findViewById(R.id.editTextCfmPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val returnToRegisterOne = findViewById<ImageButton>(R.id.backToRegisterOne)
        auth = Firebase.auth

        /**
         * CHEAT:
         */
        editTextEmail.setText(constants.SHORTCUT_EMAIL)

        buttonRegister.setOnClickListener {
            // Getting all the values from textbox

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val cfmPassword = editTextCfmPassword.text.toString()

            // Perform validation. Return immediately if passwords dont match.
            if (!validatePassword(password, cfmPassword)) return@setOnClickListener

            // Getting fields from the previous registration page.
            val nric = intent.getStringExtra(constants.NRIC)
            val nricExp = intent.getStringExtra(constants.NRIC_EXP)
            val firstName = intent.getStringExtra(constants.FIRST_NAME)
            val lastName = intent.getStringExtra(constants.LAST_NAME)


            Log.d(constants.logSignIn, email)
            Log.d(constants.logSignIn, password)
            Log.d(constants.logSignIn, cfmPassword)
            Log.d(constants.logSignIn, firstName)
            Log.d(constants.logSignIn, lastName)
            Log.d(constants.logSignIn, nric)
            Log.d(constants.logSignIn, nricExp)

            //Creating new object for mambu
             val newClient = ClientInfo(
                 firstName,
                 lastName,
                 nric,
                 nricExp
            )

            // Adding the data into FireBase
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(constants.logSignIn, "createUserWithEmail:success")
                    val user = auth.currentUser

                    // Saving the credentials into FireStore
                    toast.toastLong(this, "Registration Successful")
                    var clientInfo = ClientInfo(userId = user!!.uid, emailAddress = email, firstName = firstName, lastName = lastName, NRIC = nric, NRIC_Issued = nricExp)
                    clientInfo.createClient()
                    clientInfo.saveSharedPreference(this)

                    expeditionDAO.createEmptyExpedition(user!!.uid)

                    sharedPref =  getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
                    // Todo: Chang Wei put your Codes here!
                    //Opening New Mambu Client Info & Automatically Create Savings Account
                    OkHttpRequestHandler.createNewClient(sharedPref.getString(constants.USERNAME, "")!!, newClient)


                    // Redirecting to next page.
                    startActivity(redirectPage.razerPayActivity(this))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(constants.logSignIn, "createUserWithEmail:failure", task.exception)
                    toast.toastLong(this, "Registration Failed")
                }
            }
        }

        backToRegisterOne.setOnClickListener {
            val tempIntent = Intent(it.context, RegisterActivity::class.java)
            startActivity(tempIntent)
        }

    }

    fun validatePassword(password : String, cfmPassword : String) : Boolean{
        if(password != cfmPassword) return false
        return true
    }
}
