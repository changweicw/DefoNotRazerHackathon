package com.example.razerhackathon


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.Models.DepositInfo
import com.example.razerhackathon.Models.NewAccount
import com.example.razerhackathon.OkHttp.OkHttpRequestHandler
import com.example.razerhackathon.Volley.VolleyRequestHandler
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.PREF_NAME
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val exampleClient: ClientInfo = ClientInfo(
        "RegisterClientTrial",
        "Kotlin",
        "S9122331A",
        "2021-11-25"
    )

    private val exampleNewSavings: NewAccount = NewAccount(
        "8a8e87b772175ada01721c7ee46c2878",
    7.0,
        "2.18"
    )

    private val exampleDeposit: DepositInfo = DepositInfo(
        "250",
        "8a8e862a7217508901721815f3602105",
        "Deposit into Savings Account",
        "DEPOSIT",
        "bank"
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
//            VolleyRequestHandler.addToBalance(it, exampleDeposit)
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
        val tempIntent = Intent(view.context, LandingActivity::class.java)
        startActivity(tempIntent)
        finish()
    }

}
