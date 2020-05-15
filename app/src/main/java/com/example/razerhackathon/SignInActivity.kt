package com.example.razerhackathon

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

import com.example.razerhackathon.global.constants.Companion.logSignIn

import android.widget.Toast
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.db.userDAO


import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

// Initialize Firebase Auth
    private lateinit var textboxUsername : EditText
    private lateinit var textboxPassword : EditText
    private lateinit var Email : String
    private lateinit var Password : String

    private lateinit var clientInfo : ClientInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Setting up the views.
        textboxUsername = findViewById(R.id.textboxEmail)
        textboxPassword = findViewById(R.id.textBoxPassword)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null){
            // Create an intent and redirect user to main activity
            startActivity(redirectPage.mainActivity(this))
            finish()
        }
    }

    fun buttonSignInOnClick(view: View) {
        getLoginCredentials()
        auth.signInWithEmailAndPassword(Email, Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(logSignIn, "signInWithEmail:success")
                    toast.toastLong(this, "Logged in successfully!")
                    val user = auth.currentUser

                    val context = this
                    MainScope().launch {
                        clientInfo = userDAO.getUser(user!!.uid)
                        clientInfo.saveSharedPreference(context)
                        startActivity(redirectPage.razerPayActivity(context))
                    }
                    // Get the document from firebase
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(logSignIn, "signInWithEmail:failure", task.exception)
                    toast.toastLong(this, "Logged in failed!")
                }
            }
    }

    fun getLoginCredentials(){
        Email = textboxUsername.text.toString()
        Password = textboxPassword.text.toString()
    }

    // Redirect user to creating account
    fun buttonCreateAccountOnClick(view: View) {
        startActivity(redirectPage.registerActivity(this))
    }


}
