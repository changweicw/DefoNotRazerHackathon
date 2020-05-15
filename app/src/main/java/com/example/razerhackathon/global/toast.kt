package com.example.razerhackathon.global

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.razerhackathon.MainActivity
import com.example.razerhackathon.SignInActivity

class toast {
    companion object{
        fun toastShort(ctx : Activity, message: String){
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        }

        fun toastLong(ctx : Activity, message : String){
            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show()
        }
    }

}