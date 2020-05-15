package com.example.razerhackathon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.razerhackathon.constants.Companion.logSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

// Initialize Firebase Auth
    private lateinit var textboxUsername : EditText
    private lateinit var textboxPassword : EditText
    private lateinit var Email : String
    private lateinit var Password : String


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

    }

    fun buttonSignInOnClick(view: View) {
     getLoginCredentials()
    }
    fun buttonRegisterOnClick(view: View) {
      getLoginCredentials()
        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this){
            task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(logSignIn, "createUserWithEmail:success")
                val user = auth.currentUser
                Toast.makeText(baseContext, "Authentication successful!",
                    Toast.LENGTH_SHORT).show()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(logSignIn, "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getLoginCredentials(){
        Email = textboxUsername.text.toString()
        Password = textboxPassword.text.toString()
    }

}
