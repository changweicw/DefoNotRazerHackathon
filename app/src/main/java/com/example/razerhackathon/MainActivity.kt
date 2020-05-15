package com.example.razerhackathon


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.PREF_NAME
import android.widget.Button
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.Volley.VolleyRequestHandler
import com.example.razerhackathon.db.testDAO

import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val exampleClient: ClientInfo = ClientInfo(
        "Test",
        "Client",
        "S9911224A",
        "2021-11-12",
        "example@somewhere.com",
        "securepassword",
        "securepassword"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * Temp button for Mambu API Testing
         */
        val apiBtn = findViewById<Button>(R.id.buttonTestAPI)
        apiBtn.setOnClickListener {
//            VolleyRequestHandler.getUserProfile(it, "8a8e870b7217403d0172174bc9ca021a")
            VolleyRequestHandler.createNewProfile(it, exampleClient)
        }
    /**
     * Testing shared preference
     */
        // Get Shared preference and toast!
        val shared = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = shared.getString(constants.USERNAME, "")
        val email = shared.getString(constants.EMAIL, "")

        toast.toastShort(this, username!!)
        toast.toastShort(this, email!!)
    }



    fun buttonSignOut(view: View) {
        Firebase.auth.signOut()
        startActivity(redirectPage.signInActivity(this))
        finish()
    }

}
