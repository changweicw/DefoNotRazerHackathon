package com.example.razerhackathon

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.PREF_NAME
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
