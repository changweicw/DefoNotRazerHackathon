package com.example.razerhackathon.global

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.razerhackathon.MainActivity
import com.example.razerhackathon.SignInActivity

class redirectPage {
    companion object{
        fun mainActivity(ctx : Context) : Intent{
            val intent = Intent(ctx, MainActivity::class.java)
            return intent
        }

        fun signInActivity(ctx : Context) : Intent{
            val intent = Intent(ctx, SignInActivity::class.java)
            return intent
        }
    }

}